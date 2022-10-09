package com.mesutemre.kutuphanem.dashboard.data.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mesutemre.kutuphanem.dashboard.domain.model.DashboardKitapTurIstatistikItem

@Entity
data class KitapTurIstatistikEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    @ColumnInfo(name = "aciklama")
    val aciklama: String,

    @ColumnInfo(name = "adet")
    val adet: Int
)

fun KitapTurIstatistikEntity.convertToDashBoardKitapTurIstatistikItem(): DashboardKitapTurIstatistikItem {
    return DashboardKitapTurIstatistikItem(
        aciklama = this.aciklama,
        adet = this.adet.toFloat()
    )
}
