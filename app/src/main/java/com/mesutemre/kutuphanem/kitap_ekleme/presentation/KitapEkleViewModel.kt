package com.mesutemre.kutuphanem.kitap_ekleme.presentation

import android.Manifest
import android.graphics.BitmapFactory
import androidx.camera.core.ImageProxy
import androidx.core.net.toUri
import androidx.lifecycle.viewModelScope
import com.mesutemre.kutuphanem.kitap_detay.presentation.KitapEklemeEvent
import com.mesutemre.kutuphanem.kitap_ekleme.domain.model.CameraOpenType
import com.mesutemre.kutuphanem.kitap_ekleme.domain.model.KitapEklemeKitapModel
import com.mesutemre.kutuphanem.kitap_ekleme.domain.use_case.*
import com.mesutemre.kutuphanem.util.isMinSdk29
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import com.mesutemre.kutuphanem_base.viewmodel.BaseViewModel
import com.mesutemre.kutuphanem_ui.extensions.rotateBitmap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject


@HiltViewModel
class KitapEkleViewModel @Inject constructor(
    private val kitapAciklamaImageAnalyzer: KitapAciklamaImageAnalyzer,
    private val getKitapTurListUseCase: KitapEklemeKitapTurListUseCase,
    private val getYayinEviListUseCase: KitapEklemeYayinEviListUseCase,
    private val kitapKaydetUseCase: KitapKaydetUseCase,
    private val kitapResimYukleUseCase: KitapResimYukleUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(KitapEklemeState())
    val state: StateFlow<KitapEklemeState> = _state

    private var capturedImageFile: File? = null

    init {
        if (isMinSdk29().not()) {
            _state.update {
                it.copy(
                    kitapResimEklemePermissionList = listOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                )
            }
        }
    }

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
                        showCropArea = false,
                        croppedImageFile = event.croppedImageFile
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
            is KitapEklemeEvent.KitapResimEklemePermissionClicked -> {
                _state.update {
                    it.copy(
                        isKitapResimEklemePermissionClicked = event.kitapResimEklemeClicked,
                        cameraOpenType = CameraOpenType.KITAP_RESIM
                    )
                }
            }
            is KitapEklemeEvent.OnSaveKitap -> {
                validateKitapEklemeForm()
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
        viewModelScope.launch {
            getYayinEviListUseCase().collect { response ->
                _state.update {
                    it.copy(
                        yayinEviListResponse = response
                    )
                }
            }
        }
    }

    private fun validateKitapEklemeForm() {
        viewModelScope.launch {
            kitapKaydet()
        }
    }

    private suspend fun kitapKaydet() {
        kitapKaydetUseCase(
            kitap = KitapEklemeKitapModel(
                kitapAd = _state.value.kitapAd,
                yazarAd = _state.value.yazarAd,
                kitapAciklama = _state.value.kitapAciklama,
                alinmaTar = _state.value.alinmaTar,
                kitapTurItem = _state.value.selectedKitapTur
                    ?: throw NullPointerException("Kitap türü boş olamaz!"),
                yayinEviItem = _state.value.selectedYayinEvi
                    ?: throw NullPointerException("Yayınevi boş olamaz!")
            )
        ).collectLatest { response ->
            _state.update {
                it.copy(
                    kitapKaydetResourceEvent = response
                )
            }
            if (response is BaseResourceEvent.Success) {
                kitapResimYukleUseCase(
                    selectedFile = _state.value.croppedImageFile
                        ?: throw NullPointerException("Lütfen bir resim yükleyiniz"),
                    kitapId = response.data?.statusMessage
                        ?: throw NullPointerException("Kitap kaydı yapılamamış..."),
                ).collectLatest { resimYuklemeResponse ->
                    if (resimYuklemeResponse is BaseResourceEvent.Success) {
                        _state.value.croppedImageFile?.let {
                            it.delete()
                        }
                    }
                    _state.update {
                        it.copy(
                            kitapResimYukleResourceEvent = resimYuklemeResponse
                        )
                    }
                }
            }
        }
    }

}
