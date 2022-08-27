package com.mesutemre.kutuphanem.kitap.liste.model

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

@Keep
@Entity
data class KitapModel(
    @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = "id")
        @SerializedName("id")
        @Expose
        val kitapId:Int?,

    @ColumnInfo(name = "kitapAd")
        @SerializedName("kitapAd")
        @Expose
        var kitapAd:String?,

    @ColumnInfo(name = "yazarAd")
        @SerializedName("yazarAd")
        @Expose
    var yazarAd:String?,

    @ColumnInfo(name = "kayittarihi")
        @SerializedName("kayittarihi")
        @Expose
        var kayittarihi:Date?,

    @ColumnInfo(name = "kitapResimPath")
        @SerializedName("kitapResimPath")
        @Expose
        val kitapResimPath:String?,

    @ColumnInfo(name = "kitapAciklama")
        @SerializedName("kitapAciklama")
        @Expose
        var kitapAciklama:String?,

    @ColumnInfo(name = "alinmatarihi")
        @SerializedName("alinmatarihi")
        @Expose
        var alinmatarihi: Date?,

    @ColumnInfo(name = "kitapPuan")
        @SerializedName("kitapPuan")
        @Expose
        val kitapPuan:Float,

    @ColumnInfo(name = "begenilmis")
        @SerializedName("begenilmis")
        @Expose
        var kitapBegenilmis:Int
):Serializable {

}