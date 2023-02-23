package com.mesutemre.kutuphanem.parameter.yayinevi.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mesutemre.kutuphanem.kitap_ekleme.domain.model.KitapEklemeYayinEviItem
import com.mesutemre.kutuphanem.parameter.yayinevi.domain.model.YayinEviItem
import java.io.Serializable

data class YayinEviDto(

    @SerializedName("id")
    @Expose
    val id: Int? = null,

    @SerializedName("aciklama")
    @Expose
    val aciklama: String?,

    @SerializedName("durum")
    @Expose
    val durum: YayinEviDurum? = null

) : Serializable

fun YayinEviDto.toYayinEviItem(): YayinEviItem {
    return YayinEviItem(this.id ?: 0, this.aciklama ?: "")
}

fun YayinEviDto.toKitapEklemeYayinEviItem(): KitapEklemeYayinEviItem {
    return KitapEklemeYayinEviItem(
        yayinEviId = this.id ?: 0,
        yayinEviAciklama = this.aciklama ?: ""
    )
}
