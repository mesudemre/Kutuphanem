package com.mesutemre.kutuphanem.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.mesutemre.kutuphanem.model.ResponseStatusModel
import com.mesutemre.kutuphanem.model.YayineviModel
import com.mesutemre.kutuphanem.repositories.ParametreRepository
import com.mesutemre.kutuphanem.service.IParametreService
import com.mesutemre.kutuphanem.util.APP_TOKEN_KEY
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import com.mesutemre.kutuphanem.util.PARAM_YAYINEVI_DB_KEY
import com.mesutemre.kutuphanem.util.WebApiUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class ParametreYayineviViewModel @Inject constructor(application: Application,
                                                     private val parametreService: IParametreService,
                                                     private val parametreRepository: ParametreRepository)
    : AndroidViewModel(application), CoroutineScope {

    val yayineviListe =  MutableLiveData<List<YayineviModel>>();
    val yayinEviError = MutableLiveData<Boolean>();
    val yayinEviLoading = MutableLiveData<Boolean>();
    private val disposible = CompositeDisposable();

    val yayinEviSilResponse = MutableLiveData<ResponseStatusModel>();
    val yayinEviSilError = MutableLiveData<Boolean>();
    private lateinit var token:String;

    @Inject
    lateinit var customSharedPreferences: CustomSharedPreferences;

    private val job = Job();
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main; //Önce işi yap sonra main thread e dön.

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
        this.yayinEviLoading.value = true;
        disposible.add(
                parametreService.getYayinEviListe()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<YayineviModel>>(){
                            override fun onSuccess(response: List<YayineviModel>) {
                                yayinEviError.value = false;
                                yayinEviLoading.value = false;
                                yayineviListe.value = response;
                                storeInDatabse(response);
                            }

                            override fun onError(e: Throwable) {
                                yayinEviError.value = true;
                                yayinEviLoading.value = false;
                            }
                        }));
    }

    fun deleteYayineviParametre(jsonStr:String){
        disposible.add(
                parametreService.yayinEviKaydet(jsonStr)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<ResponseStatusModel>(){
                            override fun onSuccess(response: ResponseStatusModel) {
                                yayinEviSilError.value = false;
                                yayinEviSilResponse.value = response;
                                customSharedPreferences.removeFromSharedPreferences(PARAM_YAYINEVI_DB_KEY);
                            }

                            override fun onError(e: Throwable) {
                                yayinEviSilError.value = true;
                            }

                        }));
    }

    private fun initFromDatabase(){
        launch {
            yayineviListe.value = parametreRepository.getYayinEviListe();
            yayinEviError.value = false;
            yayinEviLoading.value = false;
        }
    }

    private fun storeInDatabse(yayinEviListe:List<YayineviModel>){
        launch {
            parametreRepository.deleteYayinEviListe();
            parametreRepository.yayinEviParametreKaydet(*yayinEviListe.toTypedArray());
            customSharedPreferences.putBooleanToSharedPreferences(PARAM_YAYINEVI_DB_KEY,true);
        }
    }

    override fun onCleared() {
        super.onCleared();
        disposible.clear();
        job.cancel();
    }

}