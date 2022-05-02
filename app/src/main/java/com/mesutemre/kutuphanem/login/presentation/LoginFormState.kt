package com.mesutemre.kutuphanem.login.presentation

import androidx.annotation.StringRes
import com.mesutemre.kutuphanem.base.BaseResourceEvent

data class LoginFormState(
    var username: String = "",
    var usernameError: Boolean = false,
    @StringRes var usernameErrorMessage: Int? = null,
    var password: String = "",
    var passwordError: Boolean = false,
    @StringRes var passwordErrorMessage: Int? = null,
    var loginResourceEvent: BaseResourceEvent<String?> = BaseResourceEvent.Nothing()
)