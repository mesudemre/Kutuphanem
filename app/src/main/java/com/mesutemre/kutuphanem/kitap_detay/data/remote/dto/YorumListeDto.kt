package com.mesutemre.kutuphanem.kitap_detay.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class YorumListeDto(

    @SerializedName("puanListe")
    @Expose
    val puanListe: List<KitapPuanDto>? = null,

    @SerializedName("yorumListe")
    @Expose
    val yorumListe: List<KitapYorumDto>? = null
)
