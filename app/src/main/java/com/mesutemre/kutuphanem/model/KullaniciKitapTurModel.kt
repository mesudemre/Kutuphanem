package com.mesutemre.kutuphanem.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class KullaniciKitapTurModel(

    @ColumnInfo(name = "ilgi_alan_id")
    @PrimaryKey
    var aciklamaId:Int,

    @ColumnInfo(name = "ilgi_alan_aciklama")
    var aciklama:String,

    @ColumnInfo(name = "username")
    var username:String
)