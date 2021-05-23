package com.mesutemre.kutuphanem.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class KitapturModel(
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var kitapTurId:Int?,

    @ColumnInfo(name = "aciklama")
    @SerializedName("aciklama")
    @Expose
    var aciklama:String?
)