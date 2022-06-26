package com.mesutemre.kutuphanem.parameter.kitaptur.data.remote.dto

import com.google.gson.annotations.SerializedName

enum class KitapTurDurum(val value:String,val label:String) {

    @SerializedName("AKTIF")
    AKTIF("AKTIF","Aktif"),

    @SerializedName("PASIF")
    PASIF("PASIF","Pasif")
}