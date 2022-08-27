package com.mesutemre.kutuphanem.parameter.yayinevi.data.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mesutemre.kutuphanem.parameter.yayinevi.domain.model.YayinEviItem

@Entity
data class YayinEviEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val yayinEviId: Int?,

    @ColumnInfo(name = "aciklama")
    var aciklama: String?
)

fun YayinEviEntity.toYayinEviItem():YayinEviItem {
    return YayinEviItem(this.yayinEviId ?: 0,this.aciklama ?: "")
}