package com.mesutemre.kutuphanem.parameter.ekleme.presentation

import androidx.lifecycle.viewModelScope
import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.base.BaseViewModel
import com.mesutemre.kutuphanem.parameter.ekleme.domain.ClearParametreCacheUseCase
import com.mesutemre.kutuphanem.parameter.ekleme.domain.ParametreEklemeValidation
import com.mesutemre.kutuphanem.parameter.ekleme.domain.SaveParametreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 10.07.2022
 */
@HiltViewModel
class ParametreEklemeViewModel @Inject constructor(
    private val parametreEklemeValidation: ParametreEklemeValidation,
    private val saveParametreUseCase: SaveParametreUseCase,
    private val clearParametreCacheUseCase: ClearParametreCacheUseCase
) : BaseViewModel() {

    private val _parametreEklemeState = MutableStateFlow(ParametreEklemeState())
    val parametreEklemeState: StateFlow<ParametreEklemeState> = _parametreEklemeState

    fun onChangeEvent(event: ParametreEklemeValidationEvent) {
        when (event) {
            is ParametreEklemeValidationEvent.ParametreTypeChanged -> {
                _parametreEklemeState.update {
                    it.copy(
                        selectedParameterType = event.selectedParameterType,
                        parametreText = "",
                        parametreTextErrorMessage = null
                    )
                }
            }
            is ParametreEklemeValidationEvent.ParametreTextChanged -> {
                _parametreEklemeState.update {
                    it.copy(
                        parametreText = event.text,
                        parametreTextErrorMessage = null
                    )
                }
            }
            is ParametreEklemeValidationEvent.ParametreFocusChanged -> {
                _parametreEklemeState.update {
                    it.copy(
                        parametreTextErrorMessage = null
                    )
                }
            }
            is ParametreEklemeValidationEvent.Submit -> {
                sumbitParametreKayitForm()
            }
        }
    }

    private fun sumbitParametreKayitForm() {
        val validationResult = parametreEklemeValidation(
            _parametreEklemeState.value.selectedParameterType,
            _parametreEklemeState.value.parametreText
        )
        val hasError = listOf(
            validationResult
        ).any {
            !it.successfullValidate
        }
        if (hasError) {
            _parametreEklemeState.update {
                it.copy(
                    parametreTextErrorMessage = validationResult.messageResId
                )
            }
            return
        }

        doParametreSave()
    }

    private fun doParametreSave() {
        viewModelScope.launch {
            saveParametreUseCase(
                _parametreEklemeState.value.selectedParameterType,
                _parametreEklemeState.value.parametreText
            ).collect { response ->
                if (response is BaseResourceEvent.Success)
                    clearParametreCacheUseCase(_parametreEklemeState.value.selectedParameterType)
                _parametreEklemeState.update {
                    it.copy(
                        parametreKayit = response,
                        parametreText = "",
                        parametreTextErrorMessage = null
                    )
                }
            }
        }
    }
}