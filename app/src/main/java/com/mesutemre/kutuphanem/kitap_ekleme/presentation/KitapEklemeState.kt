package com.mesutemre.kutuphanem.kitap_ekleme.presentation

import androidx.compose.runtime.Stable

@Stable
data class KitapEklemeState(
    val kitapAd: String = "",
    val kitapAdError: Int? = null,
    val yazarAd: String = "",
    val yazarAdError: Int? = null,
    val alinmaTar: String = "",
    val alinmaTarError: Int? = null
)
