package com.mesutemre.kutuphanem.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.mesutemre.kutuphanem.model.AccountCredentials
import com.mesutemre.kutuphanem.service.KullaniciService
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class LoginActivityViewModel @Inject constructor(application:Application,
                                                 private val kullaniciService:KullaniciService):AndroidViewModel(application),CoroutineScope {

    val token = MutableLiveData<String>();
    val loginError = MutableLiveData<Boolean>();
    val isLoading = MutableLiveData<Boolean>();
    private val disposible = CompositeDisposable();

    private val job = Job();
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main; //Önce işi yap sonra main thread e dön.

    fun doLogin(account:AccountCredentials){
        this.isLoading.value = true; //Loader visible oluyor...
        disposible.add(
            kullaniciService.login(account)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<String>(){
                    override fun onSuccess(t: String) {
                        if(t.contains("sifre hatali")){
                            isLoading.value = false;
                            loginError.value = true;
                            token.value = null;
                        }else{
                            isLoading.value = false;
                            loginError.value = false;
                            token.value = t;
                        }

                    }

                    override fun onError(e: Throwable) {
                        isLoading.value = false;
                        loginError.value = true;
                    }

                }));
    }

    override fun onCleared() {
        super.onCleared();
        disposible.clear();
        job.cancel();
    }
}