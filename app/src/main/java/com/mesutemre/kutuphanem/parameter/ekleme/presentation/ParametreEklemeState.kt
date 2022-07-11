package com.mesutemre.kutuphanem.parameter.ekleme.presentation

import com.mesutemre.kutuphanem.parameter.ekleme.presentation.components.SelectedParameterType

data class ParametreEklemeState(
    val parametreText: String = "",
    val selectedParameterType: SelectedParameterType = SelectedParameterType.YAYINEVI,
    var parametreTextError: Boolean = false,
)
