package com.mesutemre.kutuphanem.kitap_ekleme.domain.use_case

import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.kitap_ekleme.domain.model.KitapEklemeKitapTurItem
import com.mesutemre.kutuphanem.model.ValidationResult
import javax.inject.Inject

class KitapTurSelectionValidationUseCase @Inject constructor() {

    operator fun invoke(selectedKitapTur: KitapEklemeKitapTurItem?): ValidationResult {
        return selectedKitapTur?.let {
            ValidationResult(
                successfullValidate = true
            )
        } ?: run {
            ValidationResult(
                successfullValidate = false,
                messageResId = R.string.kitapTurErrorText
            )
        }
    }
}