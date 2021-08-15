package com.mesutemre.kutuphanem.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class YayineviModel(
        @PrimaryKey
        @ColumnInfo(name = "id")
        @SerializedName("id")
        @Expose
        val yayinEviId:Int?,

        @ColumnInfo(name = "aciklama")
        @SerializedName("aciklama")
        @Expose
        var aciklama:String?
) : Serializable {

        override fun toString(): String {
                return aciklama!!;
        }
}