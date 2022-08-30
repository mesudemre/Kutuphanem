package com.mesutemre.kutuphanem.parameter.kitaptur.data.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mesutemre.kutuphanem.dashboard.domain.model.DashboardKategoriItem
import com.mesutemre.kutuphanem.parameter.kitaptur.domain.model.KitapTurItem

@Entity
data class KitapTurEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val kitapTurId: Int?,

    @ColumnInfo(name = "aciklama")
    val aciklama: String?,

    @ColumnInfo(name = "resim")
    val resim: String?
)

fun KitapTurEntity.toKitapTurItem(): KitapTurItem {
    return KitapTurItem(id = this.kitapTurId ?: 0, aciklama = this.aciklama ?: "")
}

fun KitapTurEntity.toDashboardKategoriItem(): DashboardKategoriItem {
    return DashboardKategoriItem(aciklama = this.aciklama ?: "", resim = this.resim ?: "")
}