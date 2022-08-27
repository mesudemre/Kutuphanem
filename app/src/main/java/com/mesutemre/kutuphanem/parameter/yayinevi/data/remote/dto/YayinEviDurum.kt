package com.mesutemre.kutuphanem.parameter.yayinevi.data.remote.dto

import com.google.gson.annotations.SerializedName

enum class YayinEviDurum(val value:String,val label:String) {

    @SerializedName("AKTIF")
    AKTIF("AKTIF","Aktif"),

    @SerializedName("PASIF")
    PASIF("PASIF","Pasif")
}