package com.mesutemre.kutuphanem.kitap_liste.domain.model

data class KitapListeItem(
    val kitapId: Int,
    val kitapAd: String,
    val yazarAd: String,
    val kitapPuan: Float,
    val isBegenilmis: Boolean,
    val kitapAciklama: String,
    val kitapResim: String
)
