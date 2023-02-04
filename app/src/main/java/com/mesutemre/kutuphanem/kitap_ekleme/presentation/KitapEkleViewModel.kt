package com.mesutemre.kutuphanem.kitap_ekleme.presentation

import android.graphics.BitmapFactory
import androidx.camera.core.ImageProxy
import androidx.core.net.toUri
import androidx.lifecycle.viewModelScope
import com.mesutemre.kutuphanem.kitap_detay.presentation.KitapEklemeEvent
import com.mesutemre.kutuphanem.kitap_ekleme.domain.model.CameraOpenType
import com.mesutemre.kutuphanem.kitap_ekleme.domain.use_case.KitapAciklamaImageAnalyzer
import com.mesutemre.kutuphanem.kitap_ekleme.domain.use_case.KitapEklemeKitapTurListUseCase
import com.mesutemre.kutuphanem.kitap_ekleme.domain.use_case.KitapEklemeYayinEviListUseCase
import com.mesutemre.kutuphanem_base.viewmodel.BaseViewModel
import com.mesutemre.kutuphanem_ui.extensions.rotateBitmap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject


@HiltViewModel
class KitapEkleViewModel @Inject constructor(
    private val kitapAciklamaImageAnalyzer: KitapAciklamaImageAnalyzer,
    private val getKitapTurListUseCase: KitapEklemeKitapTurListUseCase,
    private val getYayinEviListUseCase: KitapEklemeYayinEviListUseCase
) : BaseViewModel() {

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
                        openCamera = event.isOpen,
                        cameraOpenType = event.cameraOpenType
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
            is KitapEklemeEvent.OnChangeBottomSheetType -> {
                _state.update {
                    it.copy(
                        bottomsheetScreen = event.bottomsheetType
                    )
                }
            }
            is KitapEklemeEvent.OnSelectKitapTur -> {
                _state.update {
                    it.copy(
                        selectedKitapTur = event.kitapEklemeKitapTurItem
                    )
                }
            }
            is KitapEklemeEvent.OnSelectYayinEvi -> {
                _state.update {
                    it.copy(
                        selectedYayinEvi = event.kitapEklemeYayinEviItem
                    )
                }
            }
        }
    }

    fun clickCameraPermission(cameraOpenType: CameraOpenType) {
        _state.update {
            it.copy(
                isCameraPermissionClicked = true,
                cameraOpenType = cameraOpenType
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

    fun setCapturedForImageTextRecognize(imageProxy: ImageProxy) {
        kitapAciklamaImageAnalyzer(image = imageProxy, onReadText = { text ->
            if (text.isNullOrEmpty()) {
                _state.update {
                    it.copy(
                        kitapAciklamaTextRecognationErrorOccured = true
                    )
                }
            } else {
                _state.update {
                    it.copy(
                        kitapAciklama = text,
                        kitapAciklamaError = null,
                        openCamera = false
                    )
                }
            }
        }, onFailureReadText = { exception ->
            _state.update {
                it.copy(
                    kitapAciklamaTextRecognationErrorOccured = true
                )
            }
        })
    }

    fun dismissTextRecognationErrorDialog() {
        _state.update {
            it.copy(
                kitapAciklamaTextRecognationErrorOccured = false,
                openCamera = false
            )
        }
    }

    fun initKitapTurList() {
        viewModelScope.launch {
            getKitapTurListUseCase().collect { response ->
                _state.update {
                    it.copy(
                        kitapTurListResponse = response
                    )
                }
            }
        }
    }

    fun initYayinEviList() {

    }
}
