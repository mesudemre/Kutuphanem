package com.mesutemre.kutuphanem.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.mesutemre.kutuphanem.model.ResponseStatusModel
import com.mesutemre.kutuphanem.service.IParametreService
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import com.mesutemre.kutuphanem.util.PARAM_KITAPTUR_DB_KEY
import com.mesutemre.kutuphanem.util.PARAM_YAYINEVI_DB_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.json.JSONObject
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class ParametreEklemeViewModel @Inject constructor(application: Application,
                               private val parametreService: IParametreService)
    : AndroidViewModel(application), CoroutineScope {

    private val job = Job();
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main; //Önce işi yap sonra main thread e dön.

    @Inject
    lateinit var customSharedPreferences: CustomSharedPreferences;

    private val disposible = CompositeDisposable();

    val parametreEklemeResponse = MutableLiveData<ResponseStatusModel>();
    val parametreEklemeError = MutableLiveData<Boolean>();
    val parametreEklemeLoading = MutableLiveData<Boolean>();

    fun parametreEkle(tip:String?,aciklama:String){
        val jsonObj: JSONObject = JSONObject();
        jsonObj.put("aciklama",aciklama);

        if(tip.equals("kitaptur")){
            kitapTurParametreEkle(jsonObj.toString());
        }else{
            yayinEviParametreEkle(jsonObj.toString());
        }
    }

    private fun kitapTurParametreEkle(json:String){
        parametreEklemeLoading.value = true;
        disposible.add(
                parametreService.kitapTurKaydet(json)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<ResponseStatusModel>(){
                            override fun onSuccess(response: ResponseStatusModel) {
                                parametreEklemeError.value = false;
                                parametreEklemeLoading.value = false;
                                parametreEklemeResponse.value = response;
                                customSharedPreferences.removeFromSharedPreferences(PARAM_KITAPTUR_DB_KEY);
                            }

                            override fun onError(e: Throwable) {
                                parametreEklemeError.value = true;
                                parametreEklemeLoading.value = false;
                            }
                        }));
    }

    private fun yayinEviParametreEkle(json:String){
        parametreEklemeLoading.value = true;
        disposible.add(
                parametreService.yayinEviKaydet(json)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<ResponseStatusModel>(){
                            override fun onSuccess(response: ResponseStatusModel) {
                                parametreEklemeError.value = false;
                                parametreEklemeLoading.value = false;
                                parametreEklemeResponse.value = response;
                                customSharedPreferences.removeFromSharedPreferences(PARAM_YAYINEVI_DB_KEY);
                            }

                            override fun onError(e: Throwable) {
                                parametreEklemeError.value = true;
                                parametreEklemeLoading.value = false;
                            }
                        }));
    }

    override fun onCleared() {
        super.onCleared();
        disposible.clear();
        job.cancel();
    }
}