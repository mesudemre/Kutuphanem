package com.mesutemre.kutuphanem.auth.login.ui

import androidx.lifecycle.viewModelScope
import com.mesutemre.kutuphanem.auth.login.model.AccountCredentials
import com.mesutemre.kutuphanem.auth.service.KullaniciService
import com.mesutemre.kutuphanem.base.BaseDataEvent
import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.base.BaseSingleLiveEvent
import com.mesutemre.kutuphanem.base.BaseViewModel
import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem.util.APP_TOKEN_KEY
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import com.mesutemre.kutuphanem.util.KULLANICI_ADI_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 23.10.2021
 */
@HiltViewModel
class LoginFragmentViewModel  @Inject constructor(@IoDispatcher private val ioDispatcher: CoroutineDispatcher,
                                                  private val kullaniciService: KullaniciService
): BaseViewModel() {

    @Inject
    lateinit var customSharedPreferences: CustomSharedPreferences

    val loginSingleResourceEvent = BaseSingleLiveEvent<BaseResourceEvent<String>>()

    fun doLogin(account: AccountCredentials){
        viewModelScope.launch {
            loginSingleResourceEvent.value = BaseResourceEvent.Loading()
            val loginResponse = serviceCall(
                call = {
                    kullaniciService.login(account)
                },
                ioDispatcher
            )
            when(loginResponse){
                is BaseDataEvent.Success->{
                    val tokenRes = loginResponse.data!!
                    if (tokenRes.contains("sifre hatali")) {
                        loginSingleResourceEvent.value = BaseResourceEvent.Error(loginResponse.data)
                    }else{
                        customSharedPreferences.putStringToSharedPreferences(APP_TOKEN_KEY,tokenRes)
                        customSharedPreferences.putStringToSharedPreferences(KULLANICI_ADI_KEY,account.username!!)
                        loginSingleResourceEvent.value = BaseResourceEvent.Success(loginResponse.data)
                    }
                }
                is BaseDataEvent.Error->{
                    loginSingleResourceEvent.value = BaseResourceEvent.Error(loginResponse.errMessage)
                }
            }
        }
    }
}