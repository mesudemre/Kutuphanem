package com.mesutemre.kutuphanem.viewmodels

import androidx.lifecycle.viewModelScope
import com.mesutemre.kutuphanem.base.BaseDataEvent
import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.base.BaseSingleLiveEvent
import com.mesutemre.kutuphanem.base.BaseViewModelLast
import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem.model.KitapturModel
import com.mesutemre.kutuphanem.model.ResponseStatusModel
import com.mesutemre.kutuphanem.repositories.ParametreRepository
import com.mesutemre.kutuphanem.service.IParametreService
import com.mesutemre.kutuphanem.util.APP_TOKEN_KEY
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import com.mesutemre.kutuphanem.util.PARAM_KITAPTUR_DB_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ParametreKitapturViewModel  @Inject constructor(
                  private val parametreService: IParametreService,
                  private val parametreRepository: ParametreRepository,
                  @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): BaseViewModelLast() {

    @Inject
    lateinit var customSharedPreferences: CustomSharedPreferences;

    private lateinit var token:String;

    val kitapTurSilResourceEvent = BaseSingleLiveEvent<BaseResourceEvent<ResponseStatusModel>>();
    val kitapTurListeResourceEvent = BaseSingleLiveEvent<BaseResourceEvent<List<KitapturModel>>>();

    fun kitapTurListeGetir(isSwipeRefresh:Boolean){
        token = customSharedPreferences.getStringFromSharedPreferences(APP_TOKEN_KEY);
        if(isSwipeRefresh){
            this.initFromService();
        }else{
            val fromDb = customSharedPreferences.getBooleanFromSharedPreferences(PARAM_KITAPTUR_DB_KEY);
            if(fromDb){
                this.initFromDatabase();
            }else{
                this.initFromService();
            }
        }
    }

    private fun initFromService(){
        viewModelScope.launch {
            kitapTurListeResourceEvent.value = BaseResourceEvent.Loading();
            val kitapTurListeResponse = serviceCall(
                call = {
                    parametreService.getKitapTurListe()
                },ioDispatcher);

            when(kitapTurListeResponse){
                is BaseDataEvent.Success->{
                    kitapTurListeResourceEvent.value = BaseResourceEvent.Success(kitapTurListeResponse.data!!);
                    storeInDatabse(kitapTurListeResponse.data!!);
                }
                is BaseDataEvent.Error->{
                    kitapTurListeResourceEvent.value = BaseResourceEvent.Error(kitapTurListeResponse.errMessage);
                }
            }
        }
    }

    private fun initFromDatabase(){
        viewModelScope.launch {
            kitapTurListeResourceEvent.value = BaseResourceEvent.Loading();
            val kitapTurListeDBResponse = dbCall(
                    call = {
                        parametreRepository.getKitapTurListe()
                    },ioDispatcher);


            when(kitapTurListeDBResponse){
                is BaseDataEvent.Success->{
                    kitapTurListeResourceEvent.value = BaseResourceEvent.Success(kitapTurListeDBResponse.data!!);
                }
                is BaseDataEvent.Error->{
                    kitapTurListeResourceEvent.value = BaseResourceEvent.Error(kitapTurListeDBResponse.errMessage)
                }
            }
        }
    }

    private suspend fun storeInDatabse(kitapTurListe:List<KitapturModel>){
        withContext(ioDispatcher) {
            parametreRepository.deleteKitapTurListe();
            parametreRepository.kitapTurParametreKaydet(*kitapTurListe.toTypedArray());
            customSharedPreferences.putBooleanToSharedPreferences(PARAM_KITAPTUR_DB_KEY,true);
        }
    }

    fun deleteKitapturParametre(jsonStr:String){
        viewModelScope.launch {
            kitapTurSilResourceEvent.value = BaseResourceEvent.Loading();
            val kitapTurParametreSilmeResponse = serviceCall(
                    call = {
                        parametreService.kitapTurKaydet(jsonStr)
                    },
                    ioDispatcher);

            when(kitapTurParametreSilmeResponse){
                is BaseDataEvent.Success->{
                    customSharedPreferences.removeFromSharedPreferences(PARAM_KITAPTUR_DB_KEY);
                    kitapTurSilResourceEvent.value = BaseResourceEvent.Success(kitapTurParametreSilmeResponse.data!!);
                }
                is BaseDataEvent.Error->{
                    kitapTurSilResourceEvent.value = BaseResourceEvent.Error(kitapTurParametreSilmeResponse.errMessage);
                }
            }
        }
    }
}