package com.mesutemre.kutuphanem.parameter.ekleme.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.mesutemre.kutuphanem.base.BaseViewModel
import com.mesutemre.kutuphanem.di.DefaultDispatcher
import com.mesutemre.kutuphanem.parameter.ekleme.presentation.components.SelectedParameterType
import com.mesutemre.kutuphanem.parameter.kitaptur.domain.use_case.DeleteKitapTurUseCase
import com.mesutemre.kutuphanem.parameter.kitaptur.domain.use_case.GetKitapTurListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 10.07.2022
 */
@HiltViewModel
class ParametreEklemeViewModel @Inject constructor(
    private val getKitapTurListUseCase: GetKitapTurListUseCase,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val deleteKitapTurUseCase: DeleteKitapTurUseCase
): BaseViewModel() {

    private val _parametreEklemeState = mutableStateOf(ParametreEklemeState())
    val parametreEklemeState: State<ParametreEklemeState> = _parametreEklemeState

    fun onChangeParameterType(selectedParameterType: SelectedParameterType) {
        _parametreEklemeState.value = _parametreEklemeState.value.copy(
            selectedParameterType = selectedParameterType
        )
    }
}