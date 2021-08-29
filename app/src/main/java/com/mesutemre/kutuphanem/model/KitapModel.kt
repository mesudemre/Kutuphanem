package com.mesutemre.kutuphanem.model

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
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
        val yazarAd:String?,

        @ColumnInfo(name = "kitapTur")
        @SerializedName("kitapTur")
        @Expose
        val kitapTur:KitapturModel?,

        @ColumnInfo(name = "yayinEvi")
        @SerializedName("yayinEvi")
        @Expose
        val yayineviModel: YayineviModel?,

        @ColumnInfo(name = "kayittarihi")
        @SerializedName("kayittarihi")
        @Expose
        val kayittarihi:Date?,

        @ColumnInfo(name = "kitapResimPath")
        @SerializedName("kitapResimPath")
        @Expose
        val kitapResimPath:String?,

        @ColumnInfo(name = "kitapAciklama")
        @SerializedName("kitapAciklama")
        @Expose
        val kitapAciklama:String?,

        @ColumnInfo(name = "alinmatarihi")
        @SerializedName("alinmatarihi")
        @Expose
        val alinmatarihi: Date?,

        @ColumnInfo(name = "kitapPuan")
        @SerializedName("kitapPuan")
        @Expose
        val kitapPuan:Float
):Serializable {
        @Ignore
        var isMenuShown:Boolean = false;

        constructor() : this(null,null,null,null,null,null,null,null,null,0f) {
        }

        constructor(kitapId:Int) : this(kitapId,null,null,null,null,null,null,null,null,0f) {
        }

}