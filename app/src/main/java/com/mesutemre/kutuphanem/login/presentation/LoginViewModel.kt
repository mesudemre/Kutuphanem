package com.mesutemre.kutuphanem.login.presentation

import androidx.lifecycle.viewModelScope
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.base.BaseViewModel
import com.mesutemre.kutuphanem.login.data.remote.dto.AccountCredentialsDto
import com.mesutemre.kutuphanem.login.domain.use_case.PasswordValidation
import com.mesutemre.kutuphanem.login.domain.use_case.UsernameValidation
import com.mesutemre.kutuphanem.login.domain.use_case.WriteTokenToPrefUseCase
import com.mesutemre.kutuphanem.login.domain.use_case.do_login.DoLoginUseCase
import com.mesutemre.kutuphanem.model.ERROR
import com.mesutemre.kutuphanem.model.SnackbarMessageEvent
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
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
    private val doLoginUseCase: DoLoginUseCase,
    private val writeTokenToPrefUseCase: WriteTokenToPrefUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(LoginFormState())
    val state: StateFlow<LoginFormState> = _state

    private val loginErrorMessageChannel = Channel<SnackbarMessageEvent>()
    val loginErrorMessage = loginErrorMessageChannel.receiveAsFlow()

    fun onLoginFormEvent(event: LoginValidationEvent) {
        when (event) {
            is LoginValidationEvent.UsernameChanged -> {
                _state.update {
                    it.copy(username = event.username, usernameError = false)
                }
            }
            is LoginValidationEvent.PasswordChanged -> {
                _state.update {
                    it.copy(password = event.password, passwordError = false)
                }
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
            _state.update {
                it.copy(
                    usernameError = !usernameValidationResult.successfullValidate,
                    usernameErrorMessage = usernameValidationResult.messageResId,
                    passwordError = !passwordValidationResult.successfullValidate,
                    passwordErrorMessage = passwordValidationResult.messageResId
                )
            }
            return
        }
        doLogin()
    }

    fun onUsernameFocusedChange() {
        _state.update { it.copy(usernameError = false) }
    }

    fun onPasswordFocusedChange() {
        _state.update { it.copy(passwordError = false) }
    }

    private fun doLogin() {
        viewModelScope.launch {
            val accountCredentialsDto =
                AccountCredentialsDto(_state.value.username, _state.value.password)
            doLoginUseCase(accountCredentialsDto).collect {
                var isLoading: Boolean = false
                var isSuccess: Boolean = false

                when (it) {
                    is BaseResourceEvent.Loading -> {
                        isLoading = true
                    }
                    is BaseResourceEvent.Success -> {
                        if (it.data!!.contains("500")) {
                            loginErrorMessageChannel.send(
                                SnackbarMessageEvent(
                                    messageId = R.string.hataliLogin,
                                    type = ERROR
                                )
                            )
                        } else {
                            writeTokenToPrefUseCase(it.data!!)
                            isSuccess = true
                        }
                    }
                    is BaseResourceEvent.Error -> {
                        loginErrorMessageChannel.send(
                            SnackbarMessageEvent(
                                message = it.message ?: "",
                                type = ERROR
                            )
                        )
                    }
                }
                _state.update {
                    it.copy(isLoading = isLoading, isSuccess = isSuccess)
                }
            }
        }
    }
}