package com.mesutemre.kutuphanem.kitap_ekleme.domain.use_case

import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.model.ValidationResult
import javax.inject.Inject

class KitapAdValidationUseCase @Inject constructor() {

    operator fun invoke(kitapAd: String): ValidationResult {
        if (kitapAd.isNullOrEmpty()) {
            return ValidationResult(
                successfullValidate = false,
                messageResId = R.string.kitapAdErrorText
            )
        }
        return ValidationResult(
            successfullValidate = true
        )
    }
}