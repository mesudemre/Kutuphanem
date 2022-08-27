package com.mesutemre.kutuphanem.parameter.ekleme.presentation

import com.mesutemre.kutuphanem.parameter.ekleme.presentation.components.SelectedParameterType

sealed class ParametreEklemeValidationEvent {

    data class ParametreTypeChanged(val selectedParameterType: SelectedParameterType): ParametreEklemeValidationEvent()
    data class ParametreTextChanged(val text: String) : ParametreEklemeValidationEvent()
    object ParametreFocusChanged:ParametreEklemeValidationEvent()
    object Submit : ParametreEklemeValidationEvent()
}
