package com.mesutemre.kutuphanem.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mesutemre.kutuphanem.model.KitapModel
import com.mesutemre.kutuphanem.model.KitapturModel
import com.mesutemre.kutuphanem.service.IKitapService
import com.mesutemre.kutuphanem.service.IParametreService
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import org.json.JSONObject
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class AnasayfaViewModel @Inject constructor(application: Application,
                                            private val parametreService: IParametreService,
                                            private val kitapService: IKitapService
): AndroidViewModel(application),CoroutineScope {

    private val job = Job();
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main; //Önce işi yap sonra main thread e dön.

    private val disposible = CompositeDisposable();

    val dashKategoriListe = MutableLiveData<List<KitapturModel>>();
    val dashKategoriListeLoading = MutableLiveData<Boolean>();
    val dashKategoriListeError = MutableLiveData<Boolean>();

    val kitapSearchResult = MutableLiveData<List<KitapModel>>();
    val kitapSearchLoading = MutableLiveData<Boolean>();

    public fun getAnasayfaDashListe(){
        viewModelScope.launch {
            async { initDashKategoriListe() }
        }
    }

    private fun initDashKategoriListe(){
        launch {
            dashKategoriListeLoading.value = true;
            disposible.add(
                parametreService.getKitapTurListe()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<List<KitapturModel>>(){
                        override fun onSuccess(response: List<KitapturModel>) {
                            dashKategoriListeError.value = false;
                            dashKategoriListeLoading.value = false;
                            dashKategoriListe.value = response;
                        }

                        override fun onError(e: Throwable) {
                            dashKategoriListeError.value = true;
                            dashKategoriListeLoading.value = false;
                        }

                    }));
        }
    }

    fun searchKitapYazar(searchText:String){
        kitapSearchLoading.value = true;
        launch {
            val jsonObj: JSONObject = JSONObject();
            jsonObj.put("kitapAd",searchText);
            jsonObj.put("yazarAd",searchText);

            disposible.add(
                kitapService.getKitapListeBySingle(jsonObj.toString())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<List<KitapModel>>(){
                        override fun onSuccess(response: List<KitapModel>) {
                            kitapSearchLoading.value = false;
                            kitapSearchResult.value = response;
                        }

                        override fun onError(e: Throwable) {
                            kitapSearchLoading.value = false;
                        }
                    }));
        }
    }

    override fun onCleared() {
        super.onCleared();
        disposible.clear();
        job.cancel();
    }
}