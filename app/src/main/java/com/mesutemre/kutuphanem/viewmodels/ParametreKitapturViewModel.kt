package com.mesutemre.kutuphanem.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.mesutemre.kutuphanem.model.KitapturModel
import com.mesutemre.kutuphanem.model.ResponseStatusModel
import com.mesutemre.kutuphanem.repositories.ParametreRepository
import com.mesutemre.kutuphanem.service.IParametreService
import com.mesutemre.kutuphanem.util.APP_TOKEN_KEY
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import com.mesutemre.kutuphanem.util.PARAM_KITAPTUR_DB_KEY
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
class ParametreKitapturViewModel  @Inject constructor(application: Application,
                                                      private val parametreService: IParametreService,
                                                      private val parametreRepository: ParametreRepository
): AndroidViewModel(application), CoroutineScope {

    private val job = Job();
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main; //Önce işi yap sonra main thread e dön.

    @Inject
    lateinit var customSharedPreferences: CustomSharedPreferences;

    private val disposible = CompositeDisposable();
    private lateinit var token:String;

    val kitapturListe = MutableLiveData<List<KitapturModel>>();
    val kitapTurError = MutableLiveData<Boolean>();
    val kitapTurLoading = MutableLiveData<Boolean>();

    val kitapTurSilResponse = MutableLiveData<ResponseStatusModel>();
    val kitapTurSilError = MutableLiveData<Boolean>();

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
        this.kitapTurLoading.value = true;
        disposible.add(
            parametreService.getKitapTurListe()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<KitapturModel>>(){
                    override fun onSuccess(response: List<KitapturModel>) {
                        kitapTurError.value = false;
                        kitapTurLoading.value = false;
                        kitapturListe.value = response;
                        storeInDatabse(response);
                    }

                    override fun onError(e: Throwable) {
                        kitapTurError.value = true;
                        kitapTurLoading.value = false;
                    }

                }));
    }

    private fun initFromDatabase(){
        launch {
            kitapturListe.value = parametreRepository.getKitapTurListe();
            kitapTurError.value = false;
            kitapTurLoading.value = false;
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
        disposible.add(
                parametreService.kitapTurKaydet(jsonStr)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<ResponseStatusModel>(){
                            override fun onSuccess(response: ResponseStatusModel) {
                                kitapTurSilError.value = false;
                                kitapTurSilResponse.value = response;
                                customSharedPreferences.removeFromSharedPreferences(PARAM_KITAPTUR_DB_KEY);
                            }

                            override fun onError(e: Throwable) {
                                kitapTurSilError.value = true;
                            }

                        }));
    }

    override fun onCleared() {
        super.onCleared();
        disposible.clear();
        job.cancel();
    }

}