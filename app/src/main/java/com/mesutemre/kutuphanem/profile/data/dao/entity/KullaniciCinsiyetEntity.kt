package com.mesutemre.kutuphanem.profile.data.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mesutemre.kutuphanem.profile.data.remote.dto.CinsiyetDto

@Entity
data class KullaniciCinsiyetEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,

    @ColumnInfo(name = "kullaniciad")
    val kullaniciAd: String?,

    @ColumnInfo(name = "cinsiyetvalue")
    val cinsiyetValue: String?,

    @ColumnInfo(name = "cinsiyetlabel")
    val cinsiyetLabel: String?,
)

fun KullaniciCinsiyetEntity.convertToKullaniciCinsiyet(): CinsiyetDto {
    return CinsiyetDto(
        value = this.cinsiyetValue,
        label = this.cinsiyetLabel
    )
}
