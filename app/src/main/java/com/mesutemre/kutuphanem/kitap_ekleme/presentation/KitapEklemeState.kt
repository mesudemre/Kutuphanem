package com.mesutemre.kutuphanem.kitap_ekleme.presentation

import android.graphics.Bitmap
import android.net.Uri
import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.ImageBitmap
import com.mesutemre.kutuphanem.kitap_ekleme.data.CameraOpenType
import com.mesutemre.kutuphanem.kitap_ekleme.domain.model.KitapEklemeKitapTurModel
import com.mesutemre.kutuphanem.kitap_ekleme.domain.model.KitapEklemeYayinEviModel

@Stable
data class KitapEklemeState(
    val kitapAd: String = "",
    @StringRes val kitapAdError: Int? = null,
    val yazarAd: String = "",
    @StringRes val yazarAdError: Int? = null,
    val alinmaTar: String = "",
    @StringRes val alinmaTarError: Int? = null,
    val isCameraPermissionClicked: Boolean = false,
    val showSettingsDialog: Boolean = false,
    val selectedKitapTur: KitapEklemeKitapTurModel? = null,
    @StringRes val kitapTurError: Int? = null,
    val selectedYayinEvi: KitapEklemeYayinEviModel? = null,
    @StringRes val yayinEviError: Int? = null,
    val kitapAciklama: String = "",
    @StringRes val kitapAciklamaError: Int? = null,
    val openCamera: Boolean = false,
    @StringRes val kameraAyarAciklama: Int? = null,
    val capturedImage: Uri? = null,
    val captureImageBitmap: Bitmap? = null,
    val cropImage: Boolean = false,
    val croppedImageBitMap: ImageBitmap? = null,
    val isCapturedKitapImageCropping: Boolean = false,
    val cropProcessCompleted: Boolean = false,
    val showCroppedImage: Boolean = false,
    val showCropArea: Boolean = false,
    val cameraOpenType: CameraOpenType = CameraOpenType.KITAP_RESIM,
    val kitapAciklamaTextRecognationErrorOccured: Boolean = false
)
