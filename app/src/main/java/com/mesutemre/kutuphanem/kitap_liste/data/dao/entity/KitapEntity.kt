package com.mesutemre.kutuphanem.kitap_liste.data.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mesutemre.kutuphanem.kitap_liste.domain.model.KitapArsivItem

@Entity
data class KitapEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val kitapId: Int?,

    @ColumnInfo(name = "kitapAd")
    val kitapAd:String?,

    @ColumnInfo(name = "yazarAd")
    val yazarAd:String?,

    @ColumnInfo(name = "kitapAciklama")
    val kitapAciklama:String?,

    @ColumnInfo(name = "kitapResimPath")
    val kitapResimPath:String?
)

fun KitapEntity.convertKitapEntityItemToKitapArsivItem(): KitapArsivItem {
    return KitapArsivItem(
        kitapId = this.kitapId ?: 0,
        kitapAd = this.kitapAd ?: "",
        yazarAd = this.yazarAd ?: "",
        kitapAciklama = this.kitapAciklama ?: "",
        kitapResim = this.kitapResimPath ?: ""
    )
}
