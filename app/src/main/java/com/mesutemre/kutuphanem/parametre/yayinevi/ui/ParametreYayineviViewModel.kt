package com.mesutemre.kutuphanem.parametre.yayinevi.ui

import androidx.lifecycle.viewModelScope
import com.mesutemre.kutuphanem.base.BaseDataEvent
import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.base.BaseSingleLiveEvent
import com.mesutemre.kutuphanem.base.BaseViewModel
import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem.model.ResponseStatusModel
import com.mesutemre.kutuphanem.parametre.yayinevi.model.YayineviModel
import com.mesutemre.kutuphanem.parametre.dao.ParametreRepository
import com.mesutemre.kutuphanem.parametre.service.IParametreService
import com.mesutemre.kutuphanem.util.APP_TOKEN_KEY
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import com.mesutemre.kutuphanem.util.PARAM_YAYINEVI_DB_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ParametreYayineviViewModel @Inject constructor(@IoDispatcher private val ioDispatcher: CoroutineDispatcher,
                                                     private val parametreService: IParametreService,
                                                     private val parametreRepository: ParametreRepository
)
    : BaseViewModel() {

    private lateinit var token:String;

    val yayinEviListeResourceEvent = BaseSingleLiveEvent<BaseResourceEvent<List<YayineviModel>>>();
    val yayinEviSilResourceEvent = BaseSingleLiveEvent<BaseResourceEvent<ResponseStatusModel>>();

    @Inject
    lateinit var customSharedPreferences: CustomSharedPreferences;

    fun yayinEviListeGetir(isSwipeRefresh:Boolean){
        token = customSharedPreferences.getStringFromSharedPreferences(APP_TOKEN_KEY);
        if(isSwipeRefresh){
            this.initFromService();
        }else{
            val fromDb = customSharedPreferences.getBooleanFromSharedPreferences(PARAM_YAYINEVI_DB_KEY);
            if(fromDb){
                this.initFromDatabase();
            }else{
                this.initFromService();
            }
        }
    }

    private fun initFromService(){
        viewModelScope.launch {
            yayinEviListeResourceEvent.value = BaseResourceEvent.Loading();
            val yayinEviListeResponse = serviceCall(
                call = {
                    parametreService.getYayinEviListe();
                },ioDispatcher);
            when(yayinEviListeResponse){
                is BaseDataEvent.Success->{
                    yayinEviListeResourceEvent.value = BaseResourceEvent.Success(yayinEviListeResponse.data!!);
                    storeInDatabse(yayinEviListeResponse.data!!);
                }
                is BaseDataEvent.Error->{
                    yayinEviListeResourceEvent.value = BaseResourceEvent.Error(yayinEviListeResponse.errMessage);
                }
            }
        }
    }

    fun deleteYayineviParametre(jsonStr:String){
        viewModelScope.launch {
            yayinEviSilResourceEvent.value = BaseResourceEvent.Loading();
            val yayinEviParametreSilmeResponse = serviceCall(
                call = {
                    parametreService.yayinEviKaydet(jsonStr)
                },ioDispatcher);
            when(yayinEviParametreSilmeResponse){
                is BaseDataEvent.Success->{
                    customSharedPreferences.removeFromSharedPreferences(PARAM_YAYINEVI_DB_KEY);
                    yayinEviSilResourceEvent.value = BaseResourceEvent.Success(yayinEviParametreSilmeResponse.data!!);
                }
                is BaseDataEvent.Error->{
                    yayinEviSilResourceEvent.value = BaseResourceEvent.Error(yayinEviParametreSilmeResponse.errMessage);
                }
            }
        }
    }

    private fun initFromDatabase(){
        viewModelScope.launch {
            yayinEviListeResourceEvent.value = BaseResourceEvent.Loading();
            val yayinEviListeDBResponse = dbCall(
                call = {
                    parametreRepository.getYayinEviListe()
                },ioDispatcher);

            when(yayinEviListeDBResponse){
                is BaseDataEvent.Success->{
                    yayinEviListeResourceEvent.value = BaseResourceEvent.Success(yayinEviListeDBResponse.data!!);
                }
                is BaseDataEvent.Error->{
                    yayinEviListeResourceEvent.value = BaseResourceEvent.Error(yayinEviListeDBResponse.errMessage);
                }
            }
        }
    }

    private suspend fun storeInDatabse(yayinEviListe:List<YayineviModel>){
        withContext(ioDispatcher) {
            parametreRepository.deleteYayinEviListe();
            parametreRepository.yayinEviParametreKaydet(*yayinEviListe.toTypedArray());
            customSharedPreferences.putBooleanToSharedPreferences(PARAM_YAYINEVI_DB_KEY,true);
        }
    }
}