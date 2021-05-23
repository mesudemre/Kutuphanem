package com.mesutemre.kutuphanem.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

data class KitapModel(
        @SerializedName("id")
        @Expose
        val id:Int?,

        @SerializedName("kitapAd")
        @Expose
        var kitapAd:String?,

        @SerializedName("yazarAd")
        @Expose
        val yazarAd:String?,

        @SerializedName("kitapTur")
        @Expose
        val kitapTur:KitapturModel?,

        @SerializedName("yayinEvi")
        @Expose
        val yayineviModel: YayineviModel?,

        @SerializedName("kayittarihi")
        @Expose
        val kayittarihi:Date?,

        @SerializedName("kitapResimPath")
        @Expose
        val kitapResimPath:String?,

        @SerializedName("kitapAciklama")
        @Expose
        val kitapAciklama:String?,

        @SerializedName("alinmatarihi")
        @Expose
        val alinmatarihi: Date?,

        @SerializedName("kitapPuan")
        @Expose
        val kitapPuan:Float
) {

    constructor() : this(null,null,null,null,null,null,null,null,null,0f) {
    }

    constructor(id:Int) : this(id,null,null,null,null,null,null,null,null,0f) {
    }
}