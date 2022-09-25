package com.mesutemre.kutuphanem.dashboard.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class KitapYilIstatistikDto(

    @SerializedName("adet")
    @Expose
    val adet:Int,

    @SerializedName("yil")
    @Expose
    val yil:Int
)
