package com.mesutemre.kutuphanem.kitap_detay.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mesutemre.kutuphanem.kitap_detay.domain.model.KitapDetayIlkYorumModel
import com.mesutemre.kutuphanem.kitap_liste.data.remote.dto.KitapDto
import com.mesutemre.kutuphanem.profile.data.remote.dto.KullaniciDto
import java.util.*

data class KitapYorumDto(

    @SerializedName("id")
    @Expose
    val id: Int? = null,

    @SerializedName("kitap")
    @Expose
    val kitap: KitapDto? = null,

    @SerializedName("yorum")
    @Expose
    val yorum: String? = null,

    @SerializedName("olusturan")
    @Expose
    val yorumYazan: KullaniciDto? = null,

    @SerializedName("olusturmaTar")
    @Expose
    val yorumYazmaTar: Date? = null,

    @SerializedName("status")
    @Expose
    val status: Int? = null,

    @SerializedName("puan")
    @Expose
    val puan: Int? = null,

    @SerializedName("kullaniciResim")
    @Expose
    val kullaniciResim: String? = null
)

fun KitapYorumDto.convertKitapYorumToKitapDetayIlkYorum(): KitapDetayIlkYorumModel {
    return KitapDetayIlkYorumModel(
        yorumAciklama = this.yorum ?: "",
        yorumYazanAdSoyad = this.yorumYazan?.ad + " " + this.yorumYazan?.soyad,
        yorumYazanResim = this.kullaniciResim
    )
}
