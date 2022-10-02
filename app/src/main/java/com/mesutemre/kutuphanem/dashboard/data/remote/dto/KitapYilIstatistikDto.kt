package com.mesutemre.kutuphanem.dashboard.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mesutemre.kutuphanem.dashboard.domain.model.DashBoardKitapYilIstatistikItem

data class KitapYilIstatistikDto(

    @SerializedName("adet")
    @Expose
    val adet:Int,

    @SerializedName("yil")
    @Expose
    val yil:Int
)

fun KitapYilIstatistikDto.convertToDashBoardKitapYilIstatistikItem(): DashBoardKitapYilIstatistikItem {
    return DashBoardKitapYilIstatistikItem(
        aciklama = yil.toString(),
        adet = adet.toFloat()
    )
}
