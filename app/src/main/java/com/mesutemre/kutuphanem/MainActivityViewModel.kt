package com.mesutemre.kutuphanem

import androidx.lifecycle.viewModelScope
import com.mesutemre.kutuphanem.base.BaseViewModel
import com.mesutemre.kutuphanem.util.APP_TOKEN_KEY
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 27.02.2022
 */
@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val customSharedPreferences: CustomSharedPreferences
):BaseViewModel() {

    private val _state = MutableStateFlow(MainActivityState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val token = customSharedPreferences.getStringFromSharedPreferences(APP_TOKEN_KEY)
            state.value.token = token
            state.value.isLoading = false
        }
    }
}