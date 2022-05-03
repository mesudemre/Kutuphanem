package com.mesutemre.kutuphanem.login.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.mesutemre.kutuphanem.base.BaseViewModel
import com.mesutemre.kutuphanem.login.data.remote.dto.AccountCredentialsDto
import com.mesutemre.kutuphanem.login.domain.use_case.PasswordValidation
import com.mesutemre.kutuphanem.login.domain.use_case.UsernameValidation
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
    private val usernameValidation: UsernameValidation,
    private val passwordValidation: PasswordValidation,
    private val doLoginUseCase: DoLoginUseCase
) : BaseViewModel() {

    private val _state = mutableStateOf(LoginFormState())
    val state: State<LoginFormState> = _state

    fun onLoginFormEvent(event: LoginValidationEvent) {
        when (event) {
            is LoginValidationEvent.UsernameChanged -> {
                _state.value = _state.value.copy(username = event.username, usernameError = false)
            }
            is LoginValidationEvent.PasswordChanged -> {
                _state.value = _state.value.copy(password = event.password, passwordError = false)
            }
            is LoginValidationEvent.Submit -> {
                sumbitLoginForm()
            }
        }
    }

    private fun sumbitLoginForm() {
        val usernameValidationResult = usernameValidation(_state.value.username)
        val passwordValidationResult = passwordValidation(_state.value.password)
        val hasError = listOf(
            usernameValidationResult,
            passwordValidationResult
        ).any {
            !it.successfullValidate
        }

        if (hasError) {
            _state.value = _state.value.copy(
                usernameError = !usernameValidationResult.successfullValidate,
                usernameErrorMessage = usernameValidationResult.messageResId,
                passwordError = !passwordValidationResult.successfullValidate,
                passwordErrorMessage = passwordValidationResult.messageResId
            )
            return
        }
        doLogin()
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