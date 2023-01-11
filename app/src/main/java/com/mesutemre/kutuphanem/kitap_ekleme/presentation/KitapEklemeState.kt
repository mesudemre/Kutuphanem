package com.mesutemre.kutuphanem.kitap_ekleme.presentation

import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
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
    val openCamera: Boolean = false
)
