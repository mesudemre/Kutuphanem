package com.mesutemre.kutuphanem.profile.data.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class KullaniciBilgiEntity(

    @PrimaryKey
    @ColumnInfo(name = "kullaniciad")
    val kullaniciAd: String,

    @ColumnInfo(name = "ad")
    val ad: String?,

    @ColumnInfo(name = "soyad")
    val soyad: String?,

    @ColumnInfo(name = "dogumtarihi")
    val dogumTarihi: Date?,

    @ColumnInfo(name = "kullaniciresim")
    val kullaniciResim: String,

    @ColumnInfo(name = "eposta")
    val eposta: String,

    @ColumnInfo(name = "ishaberdar")
    val isHaberdar: Boolean
)
