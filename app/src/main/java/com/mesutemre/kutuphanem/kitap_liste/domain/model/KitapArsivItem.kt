package com.mesutemre.kutuphanem.kitap_liste.domain.model

data class KitapArsivItem(
    val kitapId: Int,
    val kitapAd: String,
    val yazarAd: String,
    val kitapAciklama: String,
    val kitapResim: String
)
