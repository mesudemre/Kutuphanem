package com.mesutemre.kutuphanem.kitap_detay.domain.model

import java.util.*

data class KitapDetayItem(
    val kitapId: Int,
    val kitapAd: String,
    val yazarAd: String,
    val kitapResim: String,
    val kitapAciklama: String,
    val kitapPuan: Float,
    val kitapTurAd: String? = null,
    val yayinEviAd: String? = null,
    val alinmaTar: Date? = null
)
