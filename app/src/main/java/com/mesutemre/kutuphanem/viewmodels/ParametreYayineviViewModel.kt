package com.mesutemre.kutuphanem.viewmodels

import android.app.Application
import com.mesutemre.kutuphanem.base.BaseDataEvent
import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.base.BaseSingleLiveEvent
import com.mesutemre.kutuphanem.base.BaseViewModel
import com.mesutemre.kutuphanem.model.ResponseStatusModel
import com.mesutemre.kutuphanem.model.YayineviModel
import com.mesutemre.kutuphanem.repositories.ParametreRepository
import com.mesutemre.kutuphanem.service.IParametreService
import com.mesutemre.kutuphanem.util.APP_TOKEN_KEY
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import com.mesutemre.kutuphanem.util.PARAM_YAYINEVI_DB_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParametreYayineviViewModel @Inject constructor(application: Application,
                                                     private val parametreService: IParametreService,
                                                     private val parametreRepository: ParametreRepository)
    : BaseViewModel(application) {

    override val disposible: CompositeDisposable = CompositeDisposable();

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
        launch(Dispatchers.IO) {
            yayinEviListeResourceEvent.postValue(BaseResourceEvent.Loading());
            val yayinEviListeResponse = serviceCall(
                call = {
                    parametreService.getYayinEviListe();
                });
            when(yayinEviListeResponse){
                is BaseDataEvent.Success->{
                    yayinEviListeResourceEvent.postValue(BaseResourceEvent.Success(yayinEviListeResponse.data!!));
                    storeInDatabse(yayinEviListeResponse.data!!);
                }
                is BaseDataEvent.Error->{
                    yayinEviListeResourceEvent.postValue(BaseResourceEvent.Error(yayinEviListeResponse.errMessage));
                }
            }
        }
    }

    fun deleteYayineviParametre(jsonStr:String){
        launch(Dispatchers.IO) {
            yayinEviSilResourceEvent.postValue(BaseResourceEvent.Loading());
            val yayinEviParametreSilmeResponse = serviceCall(
                call = {
                    parametreService.yayinEviKaydet(jsonStr)
                });
            when(yayinEviParametreSilmeResponse){
                is BaseDataEvent.Success->{
                    customSharedPreferences.removeFromSharedPreferences(PARAM_YAYINEVI_DB_KEY);
                    yayinEviSilResourceEvent.postValue(BaseResourceEvent.Success(yayinEviParametreSilmeResponse.data!!));
                }
                is BaseDataEvent.Error->{
                    yayinEviSilResourceEvent.postValue(BaseResourceEvent.Error(yayinEviParametreSilmeResponse.errMessage));
                }
            }
        }
    }

    private fun initFromDatabase(){
        launch(Dispatchers.IO) {
            yayinEviListeResourceEvent.postValue(BaseResourceEvent.Loading());
            val yayinEviListeDBResponse = dbCall(
                call = {
                    parametreRepository.getYayinEviListe()
                });

            when(yayinEviListeDBResponse){
                is BaseDataEvent.Success->{
                    yayinEviListeResourceEvent.postValue(BaseResourceEvent.Success(yayinEviListeDBResponse.data!!));
                }
                is BaseDataEvent.Error->{
                    yayinEviListeResourceEvent.postValue(BaseResourceEvent.Error(yayinEviListeDBResponse.errMessage));
                }
            }
        }
    }

    private fun storeInDatabse(yayinEviListe:List<YayineviModel>){
        launch {
            parametreRepository.deleteYayinEviListe();
            parametreRepository.yayinEviParametreKaydet(*yayinEviListe.toTypedArray());
            customSharedPreferences.putBooleanToSharedPreferences(PARAM_YAYINEVI_DB_KEY,true);
        }
    }
}