package com.mesutemre.kutuphanem.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mesutemre.kutuphanem.model.KitapturModel
import com.mesutemre.kutuphanem.service.IParametreService
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class AnasayfaViewModel @Inject constructor(application: Application,
                                            private val parametreService: IParametreService
): AndroidViewModel(application),CoroutineScope {

    private val job = Job();
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main; //Önce işi yap sonra main thread e dön.

    private val disposible = CompositeDisposable();

    val dashKategoriListe = MutableLiveData<List<KitapturModel>>();
    val dashKategoriListeLoading = MutableLiveData<Boolean>();
    val dashKategoriListeError = MutableLiveData<Boolean>();

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

}