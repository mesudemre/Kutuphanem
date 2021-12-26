package com.mesutemre.kutuphanem.viewmodels

import android.app.Application
import com.mesutemre.kutuphanem.base.BaseDataEvent
import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.base.BaseSingleLiveEvent
import com.mesutemre.kutuphanem.base.BaseViewModel
import com.mesutemre.kutuphanem.model.KitapturModel
import com.mesutemre.kutuphanem.model.ResponseStatusModel
import com.mesutemre.kutuphanem.repositories.ParametreRepository
import com.mesutemre.kutuphanem.service.IParametreService
import com.mesutemre.kutuphanem.util.APP_TOKEN_KEY
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import com.mesutemre.kutuphanem.util.PARAM_KITAPTUR_DB_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParametreKitapturViewModel  @Inject constructor(application: Application,
                                                      private val parametreService: IParametreService,
                                                      private val parametreRepository: ParametreRepository
): BaseViewModel(application) {

    override val disposible: CompositeDisposable = CompositeDisposable();

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
        launch(Dispatchers.IO) {
            kitapTurListeResourceEvent.postValue(BaseResourceEvent.Loading());
            val kitapTurListeResponse = serviceCall(
                call = {
                    parametreService.getKitapTurListe()
                });
            when(kitapTurListeResponse){
                is BaseDataEvent.Success->{
                    kitapTurListeResourceEvent.postValue(BaseResourceEvent.Success(kitapTurListeResponse.data!!));
                    storeInDatabse(kitapTurListeResponse.data!!);
                }
                is BaseDataEvent.Error->{
                    kitapTurListeResourceEvent.postValue(BaseResourceEvent.Error(kitapTurListeResponse.errMessage));
                }
            }
        }
    }

    private fun initFromDatabase(){
        launch(Dispatchers.IO) {
            kitapTurListeResourceEvent.postValue(BaseResourceEvent.Loading());
            val kitapTurListeDBResponse = dbCall(
                call = {
                    parametreRepository.getKitapTurListe()
                });

            when(kitapTurListeDBResponse){
                is BaseDataEvent.Success->{
                    kitapTurListeResourceEvent.postValue(BaseResourceEvent.Success(kitapTurListeDBResponse.data!!));
                }
                is BaseDataEvent.Error->{
                    kitapTurListeResourceEvent.postValue(BaseResourceEvent.Error(kitapTurListeDBResponse.errMessage));
                }
            }
        }
    }

    private fun storeInDatabse(kitapTurListe:List<KitapturModel>){
        launch {
            parametreRepository.deleteKitapTurListe();
            parametreRepository.kitapTurParametreKaydet(*kitapTurListe.toTypedArray());
            customSharedPreferences.putBooleanToSharedPreferences(PARAM_KITAPTUR_DB_KEY,true);
        }
    }

    fun deleteKitapturParametre(jsonStr:String){
        launch(Dispatchers.IO) {
            kitapTurSilResourceEvent.postValue(BaseResourceEvent.Loading());
            val kitapTurParametreSilmeResponse = serviceCall(
                call = {
                    parametreService.kitapTurKaydet(jsonStr)
                });
            when(kitapTurParametreSilmeResponse){
                is BaseDataEvent.Success->{
                    customSharedPreferences.removeFromSharedPreferences(PARAM_KITAPTUR_DB_KEY);
                    kitapTurSilResourceEvent.postValue(BaseResourceEvent.Success(kitapTurParametreSilmeResponse.data!!));
                }
                is BaseDataEvent.Error->{
                    kitapTurSilResourceEvent.postValue(BaseResourceEvent.Error(kitapTurParametreSilmeResponse.errMessage));
                }
            }
        }
    }
}