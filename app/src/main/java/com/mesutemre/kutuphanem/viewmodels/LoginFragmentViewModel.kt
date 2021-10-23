package com.mesutemre.kutuphanem.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mesutemre.kutuphanem.base.BaseViewModel
import com.mesutemre.kutuphanem.model.AccountCredentials
import com.mesutemre.kutuphanem.service.KullaniciService
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 23.10.2021
 */
@HiltViewModel
class LoginFragmentViewModel  @Inject constructor(application: Application,
                                                  private val kullaniciService: KullaniciService
): BaseViewModel(application) {

    override val disposible: CompositeDisposable = CompositeDisposable();

    val token = MutableLiveData<String>();
    val loginError = MutableLiveData<Boolean>();
    val isLoading = MutableLiveData<Boolean>();

    fun doLogin(account: AccountCredentials){
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


}