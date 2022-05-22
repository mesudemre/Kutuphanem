package com.mesutemre.kutuphanem.parameter.yayinevi.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.mesutemre.kutuphanem.base.BaseViewModel
import com.mesutemre.kutuphanem.parameter.yayinevi.domain.use_case.GetYayinEviListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 20.05.2022
 */
@HiltViewModel
class ParametreYayinEviViewModel @Inject constructor(
    private val getYayinEviListUseCase: GetYayinEviListUseCase
) : BaseViewModel() {

    private val _state = mutableStateOf(ParametreYayinEviState())
    val state: State<ParametreYayinEviState> = _state

    init {
        initYayinEviList(false)
    }

    fun initYayinEviList(isSwipe: Boolean) {
        viewModelScope.launch {
            getYayinEviListUseCase(isSwipe).collect {
                _state.value = _state.value.copy(
                    yayinEviList = it
                )
            }
        }
    }

    fun onSearchTextChangeValue(text:String) {
        _state.value = _state.value.copy(yayinEviFilterText = text)
    }
}