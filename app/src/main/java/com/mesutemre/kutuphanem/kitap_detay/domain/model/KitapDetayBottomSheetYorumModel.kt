package com.mesutemre.kutuphanem.kitap_detay.domain.model

data class KitapDetayBottomSheetYorumModel(
    val yorumYazanAdSoyad: String,
    val yorumYazanResim: String? = null,
    val yorumAciklama: String,
    val yorumYazmaTarih: String
)
