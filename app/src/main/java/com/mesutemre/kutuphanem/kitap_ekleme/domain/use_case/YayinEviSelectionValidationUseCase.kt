package com.mesutemre.kutuphanem.kitap_ekleme.domain.use_case

import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.kitap_ekleme.domain.model.KitapEklemeYayinEviItem
import com.mesutemre.kutuphanem.model.ValidationResult
import javax.inject.Inject

class YayinEviSelectionValidationUseCase @Inject constructor() {

    operator fun invoke(selectedYayinEvi: KitapEklemeYayinEviItem?): ValidationResult {
        return selectedYayinEvi?.let {
            ValidationResult(
                successfullValidate = true
            )
        } ?: run {
            ValidationResult(
                successfullValidate = false,
                messageResId = R.string.yayinEviErrorText
            )
        }
    }
}