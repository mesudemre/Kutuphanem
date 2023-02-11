package com.mesutemre.kutuphanem.kitap_detay.presentation

import androidx.compose.ui.graphics.ImageBitmap
import com.mesutemre.kutuphanem.kitap_ekleme.domain.model.CameraOpenType
import com.mesutemre.kutuphanem.kitap_ekleme.domain.model.KitapEklemeBottomsheetType
import com.mesutemre.kutuphanem.kitap_ekleme.domain.model.KitapEklemeKitapTurItem
import com.mesutemre.kutuphanem.kitap_ekleme.domain.model.KitapEklemeYayinEviItem
import java.io.File

sealed class KitapEklemeEvent {

    data class KitapAdTextChange(val kitapAd: String) : KitapEklemeEvent()
    data class YazarAdTextChange(val yazarAd: String) : KitapEklemeEvent()
    data class KitapAlinmaTarTextChange(val alinmaTar: String) : KitapEklemeEvent()
    data class KitapAciklamaTextChange(val kitapAciklama: String) : KitapEklemeEvent()
    data class KitapResimEklemeOpenClose(val isOpen: Boolean, val cameraOpenType: CameraOpenType) :
        KitapEklemeEvent()

    data class OnKitapResimCropped(val croppedImage: ImageBitmap, val croppedImageFile: File?) :
        KitapEklemeEvent()

    object OnKitapResimCropClose : KitapEklemeEvent()
    object OnRemoveCroppedKitapResim : KitapEklemeEvent()
    data class OnChangeBottomSheetType(val bottomsheetType: KitapEklemeBottomsheetType) :
        KitapEklemeEvent()

    data class OnSelectKitapTur(val kitapEklemeKitapTurItem: KitapEklemeKitapTurItem?) :
        KitapEklemeEvent()

    data class OnSelectYayinEvi(val kitapEklemeYayinEviItem: KitapEklemeYayinEviItem?) :
        KitapEklemeEvent()

    object OnSaveKitap : KitapEklemeEvent()
    data class KitapResimEklemePermissionClicked(val kitapResimEklemeClicked: Boolean) :
        KitapEklemeEvent()
}
