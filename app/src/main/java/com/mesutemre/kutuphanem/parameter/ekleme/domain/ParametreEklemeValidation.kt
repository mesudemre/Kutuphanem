package com.mesutemre.kutuphanem.parameter.ekleme.domain

import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.model.ValidationResult
import com.mesutemre.kutuphanem.parameter.ekleme.presentation.components.SelectedParameterType
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 12.07.2022
 */
class ParametreEklemeValidation @Inject constructor() {

    operator fun invoke(type: SelectedParameterType, text: String): ValidationResult {
        if (text.isNullOrEmpty()) {
            return ValidationResult(
                successfullValidate = false,
                messageResId = if (type == SelectedParameterType.YAYINEVI) R.string.parametreEklemeYayinEviHata else R.string.parametreEklemeKitapTurHata
            )
        }else if (text.length<3) {
            return ValidationResult(
                successfullValidate = false,
                messageResId = R.string.parametreEklemeLengthHata
            )
        }
        return ValidationResult(successfullValidate = true)
    }
}