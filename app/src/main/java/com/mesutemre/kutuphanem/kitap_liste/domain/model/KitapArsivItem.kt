package com.mesutemre.kutuphanem.kitap_liste.domain.model

import java.util.Date

data class KitapArsivItem(
    val kitapId: Int,
    val kitapAd: String,
    val yazarAd: String,
    val kitapAciklama: String,
    val kitapResim: String,
    val yayinEviAd:String,
    val kitapTurAd:String,
    val kitapPuan:Float,
    val alinmaTar:Date
)
