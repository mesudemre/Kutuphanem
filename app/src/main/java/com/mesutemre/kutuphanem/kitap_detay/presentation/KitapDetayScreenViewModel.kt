package com.mesutemre.kutuphanem.kitap_detay.presentation

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import com.mesutemre.kutuphanem_base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class KitapDetayScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _state = MutableStateFlow(KitapDetayScreenState())
    val state: StateFlow<KitapDetayScreenState> = _state

    val kitapId = savedStateHandle.get<Int>("kitapId")
    val isFromArsiv = savedStateHandle.get<Boolean>("isArsiv")

    init {
        _state.update {
            it.copy(
                kitapId = this.kitapId ?: 0,
                isFromArsiv = this.isFromArsiv ?: false
            )
        }
        Log.d("Kitap Id : ", "" + _state.value.kitapId)
        Log.d("isFromArsiv : ", "" + _state.value.isFromArsiv)
    }
}