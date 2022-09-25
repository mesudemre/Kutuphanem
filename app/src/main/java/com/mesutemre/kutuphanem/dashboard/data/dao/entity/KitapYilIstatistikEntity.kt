package com.mesutemre.kutuphanem.dashboard.data.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class KitapYilIstatistikEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    @ColumnInfo(name = "yil")
    val yil: Int,

    @ColumnInfo(name = "adet")
    val adet: Int
)
