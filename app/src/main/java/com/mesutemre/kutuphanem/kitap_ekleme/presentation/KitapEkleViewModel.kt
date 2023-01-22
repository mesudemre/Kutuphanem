package com.mesutemre.kutuphanem.kitap_ekleme.presentation

import android.graphics.BitmapFactory
import androidx.core.net.toUri
import com.mesutemre.kutuphanem.kitap_detay.presentation.KitapEklemeEvent
import com.mesutemre.kutuphanem_base.viewmodel.BaseViewModel
import com.mesutemre.kutuphanem_ui.extensions.rotateBitmap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.io.File
import javax.inject.Inject


@HiltViewModel
class KitapEkleViewModel @Inject constructor() : BaseViewModel() {

    private val _state = MutableStateFlow(KitapEklemeState())
    val state: StateFlow<KitapEklemeState> = _state

    private var capturedImageFile: File? = null

    fun onKitapEklemeEvent(event: KitapEklemeEvent) {
        when (event) {
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
            is KitapEklemeEvent.KitapAciklamaTextChange -> {
                _state.update {
                    it.copy(
                        kitapAciklama = event.kitapAciklama,
                        kitapAciklamaError = null
                    )
                }
            }
            is KitapEklemeEvent.KitapResimEklemeOpenClose -> {
                _state.update {
                    it.copy(
                        openCamera = event.isOpen
                    )
                }
            }
            is KitapEklemeEvent.OnKitapResimCropped -> {
                _state.update {
                    it.copy(
                        croppedImageBitMap = event.croppedImage,
                        showCropArea = false
                    )
                }
            }
            is KitapEklemeEvent.OnKitapResimCropClose -> {
                capturedImageFile?.let {
                    it.delete()
                }
                _state.update {
                    it.copy(
                        showCropArea = false
                    )
                }
            }
            is KitapEklemeEvent.OnRemoveCroppedKitapResim -> {
                capturedImageFile?.let {
                    it.delete()
                }
                _state.update {
                    it.copy(
                        croppedImageBitMap = null
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

    fun showSettingsDialog(aciklama: Int) {
        _state.update {
            it.copy(
                showSettingsDialog = true,
                kameraAyarAciklama = aciklama
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

    fun setCapturedImage(imageFile: File) {
        capturedImageFile = imageFile
        val bitMap = BitmapFactory.decodeFile(imageFile.path).rotateBitmap(imageFile.absolutePath)
        _state.update {
            it.copy(
                capturedImage = imageFile.toUri(),
                captureImageBitmap = bitMap,
                openCamera = false,
                cropImage = true,
                showCropArea = true
            )
        }
    }
}
