package com.mesutemre.kutuphanem.parameter.yayinevi.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mesutemre.kutuphanem.parameter.yayinevi.data.dao.entity.toYayinEviItem
import com.mesutemre.kutuphanem.parameter.yayinevi.domain.model.YayinEviItem
import java.io.Serializable

data class YayinEviDto(

    @SerializedName("id")
    @Expose
    val id: Int?,

    @SerializedName("aciklama")
    @Expose
    var aciklama: String?
) : Serializable

fun YayinEviDto.toYayinEviItem(): YayinEviItem {
    return YayinEviItem(this.id ?: 0,this.aciklama ?: "")
}
