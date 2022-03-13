package com.mesutemre.kutuphanem.login.presentation

import android.content.Context
import android.util.Log
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 6.03.2022
 */

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext private val appContext: Context
) : BaseViewModel() {

    private val _state = MutableStateFlow(LoginFormState())
    val state:StateFlow<LoginFormState> = _state

    fun validateUsername() {
        if (_state.value?.username.isNullOrEmpty()) {
            _state.updateState {
                it.copy(usernameError = true,
                    usernameErrorMessage = appContext.getString(R.string.bosKullaniciAdiHata))
            }
        }

        if (_state.value.password.isNullOrEmpty()) {
            _state.updateState {
                it.copy(passwordError = true,
                    passwordErrorMessage = appContext.getString(R.string.bosSifreHata))
            }
        }
        Log.d("UserName",_state.value.username)
        Log.d("UserPassword",_state.value.password)
    }

    fun onChangeUsername(value:String) {
        _state.updateState {
            it.copy(username = value,usernameError = false)
        }
    }

    fun onChangePassword(value:String) {
        _state.updateState {
            it.copy(password = value,passwordError = false)
        }
    }

    fun onUsernameFocusedChange() {
        _state.updateState {
            it.copy(usernameError = false)
        }
    }

    fun onPasswordFocusedChange() {
        _state.updateState {
            it.copy(passwordError = false)
        }
    }
}