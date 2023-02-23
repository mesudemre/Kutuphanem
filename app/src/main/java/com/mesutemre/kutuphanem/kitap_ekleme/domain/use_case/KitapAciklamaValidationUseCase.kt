package com.mesutemre.kutuphanem.kitap_ekleme.domain.use_case

import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.model.ValidationResult
import javax.inject.Inject

class KitapAciklamaValidationUseCase @Inject constructor() {

    operator fun invoke(kitapAciklama: String): ValidationResult {
        if (kitapAciklama.isNullOrEmpty()) {
            return ValidationResult(
                successfullValidate = false,
                messageResId = R.string.kitapAciklamaErrorText
            )
        }
        if (kitapAciklama.length < 5) {
            return ValidationResult(
                successfullValidate = false,
                messageResId = R.string.kitap_ekleme_minCharacterKitapAciklamaError
            )
        }
        return ValidationResult(
            successfullValidate = true
        )
    }
}