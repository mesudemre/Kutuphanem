package com.mesutemre.kutuphanem.login.presentation

import android.content.Context
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.base.BaseSingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 6.03.2022
 */

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val loginState = BaseSingleLiveEvent<LoginFormState>()

    init {
        loginState.value = LoginFormState()
    }

    fun validateUsername() {
        if (loginState.value?.username.isNullOrEmpty()) {
            loginState.value = loginState.value?.copy(
                usernameError = true,
                usernameErrorMessage = appContext.getString(R.string.bosKullaniciAdiHata)
            )
        }

        if (loginState.value?.password.isNullOrEmpty()) {
            loginState.value = loginState.value?.copy(
                passwordError = true,
                passwordErrorMessage = appContext.getString(R.string.bosSifreHata)
            )
        }
        Log.d("UserName",loginState.value!!.username)
        Log.d("UserPassword",loginState.value!!.password)
    }

    fun onChangeUsername(value:String) {
        loginState.value = loginState.value?.copy(username = value,usernameError = false)
    }

    fun onChangePassword(value:String) {
        loginState.value = loginState.value?.copy(password = value,passwordError = false)
    }

    fun onUsernameFocusedChange() {
        loginState.value = loginState.value?.copy(usernameError = false)
    }

    fun onPasswordFocusedChange() {
        loginState.value = loginState.value?.copy(passwordError = false)
    }
}