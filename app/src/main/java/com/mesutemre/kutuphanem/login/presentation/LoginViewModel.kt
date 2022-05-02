package com.mesutemre.kutuphanem.login.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.base.BaseViewModel
import com.mesutemre.kutuphanem.login.data.remote.dto.AccountCredentialsDto
import com.mesutemre.kutuphanem.login.domain.use_case.do_login.DoLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 6.03.2022
 */

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val doLoginUseCase: DoLoginUseCase
) : BaseViewModel() {

    private val _state = mutableStateOf(LoginFormState())
    val state: State<LoginFormState> = _state

    fun validateUsername() {
        if (_state.value?.username.isNullOrEmpty()) {
            /*sendSnackbarMessage(SnackbarMessageEvent(
                message = appContext.getString(R.string.bosKullaniciAdiHata),
                type = WARNING
            ))*/
            _state.value = _state.value.copy(
                usernameError = true,
                usernameErrorMessage = R.string.bosKullaniciAdiHata
            )
            return;
        }

        if (_state.value.password.isNullOrEmpty()) {
            _state.value = _state.value.copy(
                passwordError = true,
                passwordErrorMessage = R.string.bosSifreHata
            )
            return;
        }
        doLogin()
    }

    fun onChangeUsername(value: String) {
        _state.value = _state.value.copy(username = value, usernameError = false)
    }

    fun onChangePassword(value: String) {
        _state.value = _state.value.copy(password = value, passwordError = false)
    }

    fun onUsernameFocusedChange() {
        _state.value = _state.value.copy(usernameError = false)
    }

    fun onPasswordFocusedChange() {
        _state.value = _state.value.copy(passwordError = false)
    }

    private fun doLogin() {
        viewModelScope.launch {
            val accountCredentialsDto =
                AccountCredentialsDto(_state.value.username, _state.value.password)
            doLoginUseCase(accountCredentialsDto).collect {
                _state.value = _state.value.copy(loginResourceEvent = it)
            }
        }
    }
}