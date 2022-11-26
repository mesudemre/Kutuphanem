package com.mesutemre.kutuphanem.kitap_liste.data.dao.entity

import androidx.room.*
import com.mesutemre.kutuphanem.kitap_liste.domain.model.KitapArsivItem
import com.mesutemre.kutuphanem.parameter.kitaptur.data.dao.entity.KitapTurEntity
import com.mesutemre.kutuphanem.parameter.yayinevi.data.dao.entity.YayinEviEntity
import java.util.*

@Entity(
    foreignKeys = [ForeignKey(
        entity = KitapTurEntity::class,
        onUpdate = ForeignKey.CASCADE,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("kitapTur")
    ), ForeignKey(
        entity = YayinEviEntity::class,
        onUpdate = ForeignKey.CASCADE,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("yayinEvi")
    )]
)
data class KitapEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val kitapId: Int?,

    @ColumnInfo(name = "kitapAd")
    val kitapAd: String?,

    @ColumnInfo(name = "yazarAd")
    val yazarAd: String?,

    @ColumnInfo(name = "kitapAciklama")
    val kitapAciklama: String?,

    @ColumnInfo(name = "kitapResimPath")
    val kitapResimPath: String?,

    @ColumnInfo(name = "kitapPuan")
    val kitapPuan: Float = 0f,

    @ColumnInfo(name = "yayinEvi")
    val yayinEviId: Int? = null,

    @ColumnInfo(name = "kitapTur")
    val kitapTurId: Int? = null,

    @ColumnInfo(name = "alinmaTar")
    val alinmaTar: Date? = null
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