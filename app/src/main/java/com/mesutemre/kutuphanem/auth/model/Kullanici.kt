package com.mesutemre.kutuphanem.model

import androidx.annotation.Keep
import androidx.room.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName;
import com.mesutemre.kutuphanem.converters.Converters
import java.io.Serializable
import java.util.*

@Keep
@Entity
data class Kullanici(

    @PrimaryKey
    @ColumnInfo(name = "username")
    @SerializedName("username")
    @Expose
    var username:String,

    @SerializedName("ad")
    @ColumnInfo(name = "ad")
    @Expose
    var ad:String,

    @SerializedName("soyad")
    @ColumnInfo(name = "soyad")
    @Expose
    var soyad:String,

    @SerializedName("dogumTarihi")
    @ColumnInfo(name = "dogumTarihi")
    @Expose
    var dogumTarihi:Date,

    @SerializedName("resim")
    @ColumnInfo(name = "resim")
    @Expose
    var resim:String,

    @SerializedName("eposta")
    @ColumnInfo(name = "eposta")
    @Expose
    var eposta:String,

    @SerializedName("cinsiyet")
    @ColumnInfo(name = "cinsiyet")
    @Expose
    var cinsiyet:CinsiyetModel,

    @SerializedName("haberdarmi")
    @ColumnInfo(name = "haberdarmi")
    @Expose
    var haberdarmi:Boolean
): Serializable {
    @Ignore
    @SerializedName("ilgiAlanlari")
    @Expose
    var ilgiAlanlari:List<KitapturModel>?=null
}