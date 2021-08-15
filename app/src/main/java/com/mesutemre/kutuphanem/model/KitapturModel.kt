package com.mesutemre.kutuphanem.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class KitapturModel(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var kitapTurId:Int?,

    @ColumnInfo(name = "aciklama")
    @SerializedName("aciklama")
    @Expose
    var aciklama:String?
): Serializable {
    @SerializedName("resim")
    @Ignore
    @Expose
    var resim:String? = null

    override fun toString(): String {
        return aciklama!!;
    }
}