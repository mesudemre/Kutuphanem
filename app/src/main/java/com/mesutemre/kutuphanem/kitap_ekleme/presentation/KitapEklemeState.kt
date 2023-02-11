package com.mesutemre.kutuphanem.kitap_ekleme.presentation

import android.Manifest
import android.graphics.Bitmap
import android.net.Uri
import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.ImageBitmap
import com.mesutemre.kutuphanem.kitap_ekleme.domain.model.CameraOpenType
import com.mesutemre.kutuphanem.kitap_ekleme.domain.model.KitapEklemeBottomsheetType
import com.mesutemre.kutuphanem.kitap_ekleme.domain.model.KitapEklemeKitapTurItem
import com.mesutemre.kutuphanem.kitap_ekleme.domain.model.KitapEklemeYayinEviItem
import com.mesutemre.kutuphanem.model.ResponseStatusModel
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import java.io.File

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
    val selectedKitapTur: KitapEklemeKitapTurItem? = null,
    @StringRes val kitapTurError: Int? = null,
    val selectedYayinEvi: KitapEklemeYayinEviItem? = null,
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
    val kitapAciklamaTextRecognationErrorOccured: Boolean = false,
    val bottomsheetScreen: KitapEklemeBottomsheetType = KitapEklemeBottomsheetType.NONE,
    val kitapTurListResponse: BaseResourceEvent<List<KitapEklemeKitapTurItem>> = BaseResourceEvent.Loading(),
    val yayinEviListResponse: BaseResourceEvent<List<KitapEklemeYayinEviItem>> = BaseResourceEvent.Loading(),
    val kitapKaydetResourceEvent: BaseResourceEvent<ResponseStatusModel?> = BaseResourceEvent.Nothing(),
    val croppedImageFile: File? = null,
    val kitapResimYukleResourceEvent: BaseResourceEvent<ResponseStatusModel?> = BaseResourceEvent.Nothing(),
    val isKitapResimEklemePermissionClicked: Boolean = false,
    val kitapResimEklemePermissionList: List<String> = listOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
)
