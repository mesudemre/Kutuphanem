package com.mesutemre.kutuphanem.parameter.ekleme.presentation

import androidx.annotation.StringRes
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import com.mesutemre.kutuphanem.model.ResponseStatusModel
import com.mesutemre.kutuphanem.parameter.ekleme.presentation.components.SelectedParameterType

data class ParametreEklemeState(
    val parametreText: String = "",
    val selectedParameterType: SelectedParameterType = SelectedParameterType.YAYINEVI,
    @StringRes val parametreTextErrorMessage: Int? = null,
    val parametreKayit: BaseResourceEvent<ResponseStatusModel?> = BaseResourceEvent.Nothing()
)
