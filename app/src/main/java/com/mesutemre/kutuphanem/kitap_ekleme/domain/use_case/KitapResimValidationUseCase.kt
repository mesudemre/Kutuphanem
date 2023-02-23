package com.mesutemre.kutuphanem.kitap_ekleme.domain.use_case

import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.model.ValidationResult
import java.io.File
import javax.inject.Inject

class KitapResimValidationUseCase @Inject constructor() {

    operator fun invoke(croppedImageFile: File?): ValidationResult {
        return croppedImageFile?.let {
            ValidationResult(
                successfullValidate = true
            )
        } ?: run {
            ValidationResult(
                successfullValidate = false,
                messageResId = R.string.kitapResimErrorText
            )
        }
    }
}