package com.mesutemre.kutuphanem.parameter.kitaptur.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mesutemre.kutuphanem.dashboard.domain.model.DashboardKategoriItem
import com.mesutemre.kutuphanem.kitap_ekleme.domain.model.KitapEklemeKitapTurItem
import com.mesutemre.kutuphanem.parameter.kitaptur.domain.model.KitapTurItem
import java.io.Serializable

data class KitapTurDto(
    @SerializedName("id")
    @Expose
    val id: Int? = null,

    @SerializedName("aciklama")
    @Expose
    val aciklama: String?,

    @SerializedName("resim")
    @Expose
    val resim: String? = null,

    @SerializedName("durum")
    @Expose
    val durum: KitapTurDurum? = null
) : Serializable

fun KitapTurDto.toKitapTurItem(): KitapTurItem {
    return KitapTurItem(id = this.id ?: 0, aciklama = this.aciklama ?: "")
}

fun KitapTurDto.toDashboardKategoriItem(): DashboardKategoriItem {
    return DashboardKategoriItem(aciklama = this.aciklama ?: "", resim = this.resim ?: "")
}

fun KitapTurDto.toKitapEklemeKitapTurItem(): KitapEklemeKitapTurItem {
    return KitapEklemeKitapTurItem(
        kitapTurId = this.id ?: 0,
        kitapTurAciklama = this.aciklama ?: ""
    )
}