package com.mesutemre.kutuphanem.login.domain.use_case

import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.model.ValidationResult
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 3.05.2022
 */
class PasswordValidation @Inject constructor(){

    operator fun invoke(password:String):ValidationResult {
        if (password.isNullOrEmpty()) {
            return ValidationResult(successfullValidate = false,messageResId = R.string.bosSifreHata)
        }
        return ValidationResult(successfullValidate = true)
    }
}