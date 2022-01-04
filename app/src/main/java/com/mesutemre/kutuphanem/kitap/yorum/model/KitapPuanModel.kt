package com.mesutemre.kutuphanem.kitap.yorum.model

import com.mesutemre.kutuphanem.kitap.liste.model.KitapModel

data class KitapPuanModel(
    val kitap: KitapModel,
    val puan:Int
)
