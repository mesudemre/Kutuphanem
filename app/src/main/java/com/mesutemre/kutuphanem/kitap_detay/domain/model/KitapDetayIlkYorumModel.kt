package com.mesutemre.kutuphanem.kitap_detay.domain.model

data class KitapDetayIlkYorumModel(
    val yorumAciklama: String,
    val yorumYazanAdSoyad: String,
    val yorumYazanResim: String? = null
)
