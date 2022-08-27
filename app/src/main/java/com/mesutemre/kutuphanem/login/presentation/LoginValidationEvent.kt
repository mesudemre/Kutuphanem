package com.mesutemre.kutuphanem.login.presentation

sealed class LoginValidationEvent {

    data class UsernameChanged(val username: String) : LoginValidationEvent()
    data class PasswordChanged(val password: String) : LoginValidationEvent()
    object Submit : LoginValidationEvent()
}
