package com.mesutemre.kutuphanem.kitap_liste.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mesutemre.kutuphanem.dashboard_search.domain.model.KitapSearchItem
import com.mesutemre.kutuphanem.kitap_detay.domain.model.KitapDetayItem
import com.mesutemre.kutuphanem.kitap_liste.domain.model.KitapListeItem
import com.mesutemre.kutuphanem.parameter.kitaptur.data.remote.dto.KitapTurDto
import com.mesutemre.kutuphanem.parameter.yayinevi.data.remote.dto.YayinEviDto
import java.util.*

data class KitapDto(

    @SerializedName("id")
    @Expose
    val id: Int? = null,

    @SerializedName("kitapAd")
    @Expose
    val kitapAd: String? = null,

    @SerializedName("yazarAd")
    @Expose
    val yazarAd: String? = null,

    @SerializedName("kitapTur")
    @Expose
    val kitapTur: KitapTurDto? = null,

    @SerializedName("yayinEvi")
    @Expose
    val yayinEvi: YayinEviDto? = null,

    @SerializedName("alinmatarihi")
    @Expose
    val alinmaTar: Date? = null,

    @SerializedName("kayittarihi")
    @Expose
    val kayitTar: Date? = null,

    @SerializedName("minKayitNum")
    @Expose
    val minKayitNum: Int? = null,

    @SerializedName("maxKayitNum")
    @Expose
    val maxKayitNum: Int? = null,

    @SerializedName("kitapResimPath")
    @Expose
    val kitapResim: String? = null,

    @SerializedName("kitapAciklama")
    @Expose
    val kitapAciklama: String? = null,

    @SerializedName("paginationNum")
    @Expose
    val paginationNum: Int? = null,

    @SerializedName("kitapPuan")
    @Expose
    val kitapPuan: Float? = null,

    @SerializedName("begenilmis")
    @Expose
    val begenilmis: Int? = null

)

fun KitapDto.convertKitapDtoToKitapSearchItem(): KitapSearchItem {
    return KitapSearchItem(
        kitapId = this.id ?: 0,
        kitapAd = this.kitapAd ?: "",
        yazarAd = this.yazarAd ?: ""
    )
}

fun KitapDto.convertKitapDtoToKitapListeItem(): KitapListeItem {
    return KitapListeItem(
        kitapId = this.id ?: 0,
        kitapAd = this.kitapAd ?: "",
        yazarAd = this.yazarAd ?: "",
        kitapAciklama = this.kitapAciklama ?: "",
        kitapResim = this.kitapResim ?: "",
        isBegenilmis = this.begenilmis?.let {
            it > 0
        } ?: kotlin.run {
            false
        },
        kitapPuan = this.kitapPuan ?: 0f
    )
}

fun KitapDto.convertKitapDtoToKitapDetayItem(): KitapDetayItem {
    return KitapDetayItem(
        kitapId = this.id ?: 0,
        kitapAd = this.kitapAd ?: "",
        yazarAd = this.yazarAd ?: "",
        kitapAciklama = this.kitapAciklama ?: "",
        kitapResim = this.kitapResim ?: "",
        kitapPuan = this.kitapPuan ?: 0f,
        yayinEviAd = this.yayinEvi?.aciklama,
        kitapTurAd = this.kitapTur?.aciklama,
        alinmaTar = this.alinmaTar
    )
}
