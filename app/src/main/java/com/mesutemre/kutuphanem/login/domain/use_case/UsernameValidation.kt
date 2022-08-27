package com.mesutemre.kutuphanem.login.domain.use_case

import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.model.ValidationResult
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 3.05.2022
 */
class UsernameValidation @Inject constructor(){

    operator fun invoke(username: String): ValidationResult {
        if (username.isNullOrEmpty()) {
            return ValidationResult(
                successfullValidate = false,
                messageResId = R.string.bosKullaniciAdiHata
            )
        }
        return ValidationResult(successfullValidate = true)
    }
}