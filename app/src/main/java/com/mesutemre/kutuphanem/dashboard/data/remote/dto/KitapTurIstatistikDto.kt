package com.mesutemre.kutuphanem.dashboard.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mesutemre.kutuphanem.dashboard.domain.model.DashboardKitapTurIstatistikItem

data class KitapTurIstatistikDto(
    @SerializedName("aciklama")
    @Expose
    val aciklama: String,

    @SerializedName("adet")
    @Expose
    val adet: Int
)

fun KitapTurIstatistikDto.convertToDashboardKitapTurIstatistikItem(): DashboardKitapTurIstatistikItem {
    return DashboardKitapTurIstatistikItem(
        aciklama = this.aciklama,
        adet = this.adet.toFloat()
    )
}
