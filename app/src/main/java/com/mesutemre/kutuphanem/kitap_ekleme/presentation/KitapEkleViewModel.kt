package com.mesutemre.kutuphanem.kitap_ekleme.presentation

import com.mesutemre.kutuphanem.kitap_detay.presentation.KitapEklemeEvent
import com.mesutemre.kutuphanem_base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class KitapEkleViewModel @Inject constructor() : BaseViewModel() {

    private val _state = MutableStateFlow(KitapEklemeState())
    val state: StateFlow<KitapEklemeState> = _state

    fun onKitapEklemeEvent(event: KitapEklemeEvent) {
        when(event) {
            is KitapEklemeEvent.KitapAdTextChange -> {
                _state.update {
                    it.copy(
                        kitapAd = event.kitapAd,
                        kitapAdError = null
                    )
                }
            }
            is KitapEklemeEvent.YazarAdTextChange -> {
                _state.update {
                    it.copy(
                        yazarAd = event.yazarAd,
                        yazarAdError = null
                    )
                }
            }
            is KitapEklemeEvent.KitapAlinmaTarTextChange -> {
                _state.update {
                    it.copy(
                        alinmaTar = event.alinmaTar,
                        alinmaTarError = null
                    )
                }
            }
        }
    }

    fun clickCameraPermission() {
        _state.update {
            it.copy(
                isCameraPermissionClicked = true
            )
        }
    }

    fun showSettingsDialog() {
        _state.update {
            it.copy(
                showSettingsDialog = true
            )
        }
    }

    fun dismissSettingsDialog() {
        _state.update {
            it.copy(
                showSettingsDialog = false
            )
        }
    }
}