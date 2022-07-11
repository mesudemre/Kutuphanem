package com.mesutemre.kutuphanem.parameter.kitaptur.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mesutemre.kutuphanem.parameter.kitaptur.domain.model.KitapTurItem
import java.io.Serializable

data class KitapTurDto(
    @SerializedName("id")
    @Expose
    val id: Int?,

    @SerializedName("aciklama")
    @Expose
    val aciklama: String?,

    @SerializedName("resim")
    @Expose
    val resim: String?,

    @SerializedName("durum")
    @Expose
    val durum: KitapTurDurum?
) : Serializable

fun KitapTurDto.toKitapTurItem(): KitapTurItem {
    return KitapTurItem(id = this.id ?: 0, aciklama = this.aciklama ?: "")
}