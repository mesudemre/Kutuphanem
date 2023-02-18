package com.mesutemre.kutuphanem.kitap_ekleme.domain.use_case

import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.model.ValidationResult
import javax.inject.Inject

class YazarAdValidationUseCase @Inject constructor() {

    operator fun invoke(yazarAd: String): ValidationResult {
        if (yazarAd.isNullOrEmpty()) {
            return ValidationResult(
                successfullValidate = false,
                messageResId = R.string.yazarAdErrorText
            )
        }
        return ValidationResult(
            successfullValidate = true
        )
    }
}