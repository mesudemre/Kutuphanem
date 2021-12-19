package com.mesutemre.kutuphanem.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mesutemre.kutuphanem.base.BaseDataEvent
import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.base.BaseSingleLiveEvent
import com.mesutemre.kutuphanem.base.BaseViewModel
import com.mesutemre.kutuphanem.model.AccountCredentials
import com.mesutemre.kutuphanem.service.KullaniciService
import com.mesutemre.kutuphanem.util.APP_TOKEN_KEY
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import com.mesutemre.kutuphanem.util.KULLANICI_ADI_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 23.10.2021
 */
@HiltViewModel
class LoginFragmentViewModel  @Inject constructor(application: Application,
                                                  private val kullaniciService: KullaniciService
): BaseViewModel(application) {

    @Inject
    lateinit var customSharedPreferences: CustomSharedPreferences;

    override val disposible: CompositeDisposable = CompositeDisposable();

    val loginSingleResourceEvent = BaseSingleLiveEvent<BaseResourceEvent<String>>();

    fun doLogin(account: AccountCredentials){
        launch(Dispatchers.IO) {
            loginSingleResourceEvent.postValue(BaseResourceEvent.Loading());
            val loginResponse = serviceCall(
                call = {
                    kullaniciService.login(account);
                });
            when(loginResponse){
                is BaseDataEvent.Success->{
                    val tokenRes = loginResponse.data!!;
                    if (tokenRes.contains("sifre hatali")) {
                        loginSingleResourceEvent.postValue(BaseResourceEvent.Error(loginResponse.data!!));
                    }else{
                        customSharedPreferences.putStringToSharedPreferences(APP_TOKEN_KEY,tokenRes);
                        customSharedPreferences.putStringToSharedPreferences(KULLANICI_ADI_KEY,account.username!!);
                        loginSingleResourceEvent.postValue(BaseResourceEvent.Success(loginResponse.data!!));
                    }
                }
                is BaseDataEvent.Error->{
                    loginSingleResourceEvent.postValue(BaseResourceEvent.Error(loginResponse.errMessage));
                }
            }
        }
    }
}