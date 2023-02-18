package com.mesutemre.kutuphanem.kitap_ekleme.domain.use_case

import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.model.ValidationResult
import java.util.*
import javax.inject.Inject

class AlinmaTarValidationUseCase @Inject constructor() {

    operator fun invoke(alinmaTar: String): ValidationResult {
        if (alinmaTar.isNullOrEmpty()) {
            return ValidationResult(
                successfullValidate = false,
                messageResId = R.string.alinmaTarErrorText
            )
        }
        val day = alinmaTar.subSequence(0, 2).toString()
        val month = alinmaTar.subSequence(2, 4).toString()
        val year = alinmaTar.subSequence(4, 8).toString()

        if (day.toInt() == 0 || month.toInt() == 0 || year.toInt() == 0) {
            return ValidationResult(
                successfullValidate = false,
                messageResId = R.string.kitap_ekleme_alinmaTar_zero_value_error
            )
        }

        if (day.toInt() > 31) {
            return ValidationResult(
                successfullValidate = false,
                messageResId = R.string.alinmaTarDayErrorText
            )
        }

        if (month.toInt() > 12) {
            return ValidationResult(
                successfullValidate = false,
                messageResId = R.string.alinmaTarMonthErrorText
            )
        }

        if (year.toInt() > Calendar.getInstance().get(Calendar.YEAR) && year.toInt() < 1900) {
            return ValidationResult(
                successfullValidate = false,
                messageResId = R.string.alinmaTarYearErrorText
            )
        }

        return ValidationResult(
            successfullValidate = true
        )
    }
}