package com.mesutemre.kutuphanem.profile.data.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mesutemre.kutuphanem.profile.domain.model.KullaniciIlgiAlanModel

@Entity
data class KullaniciIlgiAlanEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,

    @ColumnInfo(name = "kullaniciad")
    val kullaniciAd: String?,

    @ColumnInfo(name = "ilgialanid")
    val ilgiAlanId: Int?,

    @ColumnInfo(name = "ilgialan")
    val ilgiAlan: String?,

    @ColumnInfo(name = "ilgialanresim")
    val ilgiAlanResim: String?
)

fun KullaniciIlgiAlanEntity.convertToKullaniciIlgiAlanModel(): KullaniciIlgiAlanModel {
    return KullaniciIlgiAlanModel(
        id = this.ilgiAlanId ?: 0,
        ilgiAlan = this.ilgiAlan ?: "",
        resim = this.ilgiAlanResim ?: ""
    )
}
