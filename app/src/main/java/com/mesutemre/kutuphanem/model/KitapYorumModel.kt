package com.mesutemre.kutuphanem.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class KitapYorumModel(

    @SerializedName("id")
    @Expose
    val id:Int?,

    @SerializedName("kitap")
    @Expose
    val kitap:KitapModel?,

    @SerializedName("yorum")
    @Expose
    val yorum:String?,

    @SerializedName("kullaniciResim")
    @Expose
    val kullaniciResim:String?,

    @SerializedName("olusturan")
    @Expose
    val olusturan:Kullanici?
): Serializable
