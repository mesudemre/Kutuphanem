package com.mesutemre.kutuphanem.kitap_detay.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mesutemre.kutuphanem.kitap_liste.data.remote.dto.KitapDto
import java.util.*

data class KitapPuanDto(

    @SerializedName("id")
    @Expose
    val id: Int? = null,

    @SerializedName("kitap")
    @Expose
    val kitap: KitapDto? = null,

    @SerializedName("puan")
    @Expose
    val puan: Int? = null,

    @SerializedName("adet")
    @Expose
    val adet: Int? = null


)
