package com.mesutemre.kutuphanem.kitap_ekleme.domain.model

data class KitapEklemeKitapModel(
    val kitapAd: String,
    val yazarAd: String,
    val alinmaTar: String,
    val kitapAciklama: String,
    val kitapTurItem: KitapEklemeKitapTurItem,
    val yayinEviItem: KitapEklemeYayinEviItem
)
