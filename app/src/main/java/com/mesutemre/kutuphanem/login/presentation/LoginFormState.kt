package com.mesutemre.kutuphanem.login.presentation

import com.mesutemre.kutuphanem.base.BaseResourceEvent

data class LoginFormState(
    var username: String = "",
    var usernameError: Boolean = false,
    var usernameErrorMessage: String? = null,
    var password: String = "",
    var passwordError: Boolean = false,
    var passwordErrorMessage: String? = null,
    var loginResourceEvent: BaseResourceEvent<String?> = BaseResourceEvent.Nothing()
)

