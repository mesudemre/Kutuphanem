package com.mesutemre.kutuphanem_base.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * @Author: mesutemre.celenk
 * @Date: 31.12.2021
 */
abstract class BaseViewModel: ViewModel() {

    private val message = Channel<String>()
    val messageFlow = message.receiveAsFlow()

    fun triggerEvent() = viewModelScope.launch {
        message.send("MESAJ.Merhaba.BaseViewModel")
    }

}