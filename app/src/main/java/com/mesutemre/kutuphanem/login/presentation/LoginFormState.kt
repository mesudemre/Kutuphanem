package com.mesutemre.kutuphanem.login.presentation

import androidx.annotation.StringRes

data class LoginFormState(
    var username: String = "",
    var usernameError: Boolean = false,
    @StringRes var usernameErrorMessage: Int? = null,
    var password: String = "",
    var passwordError: Boolean = false,
    @StringRes var passwordErrorMessage: Int? = null,
    val isLoading:Boolean = false,
    val isSuccess:Boolean = false
)