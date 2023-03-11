package com.mesutemre.kutuphanem.profile.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mesutemre.kutuphanem.dashboard.domain.model.DashboardKullaniciBilgiData
import com.mesutemre.kutuphanem.parameter.kitaptur.data.remote.dto.KitapTurDto
import com.mesutemre.kutuphanem.parameter.kitaptur.data.remote.dto.toKullaniciIlgiAlan
import com.mesutemre.kutuphanem.profile.domain.model.KullaniciBilgiModel
import com.mesutemre.kutuphanem.util.convertDate2String
import java.util.*

data class KullaniciDto(

    @SerializedName("username")
    @Expose
    var username: String,

    @SerializedName("ad")
    @Expose
    var ad: String,

    @SerializedName("soyad")
    @Expose
    var soyad: String,

    @SerializedName("dogumTarihi")
    @Expose
    var dogumTarihi: Date,

    @SerializedName("resim")
    @Expose
    var resim: String,

    @SerializedName("eposta")
    @Expose
    var eposta: String,

    @SerializedName("cinsiyet")
    @Expose
    var cinsiyet: CinsiyetDto,

    @SerializedName("haberdarmi")
    @Expose
    var haberdarmi: Boolean,

    @SerializedName("ilgiAlanlari")
    @Expose
    var ilgiAlanlari: List<KitapTurDto>
)

fun KullaniciDto.toDashBoardKullaniciBilgi(): DashboardKullaniciBilgiData {
    return DashboardKullaniciBilgiData(
        adSoyad = this.ad + " " + this.soyad,
        resim = this.resim,
        kullaniciAd = this.username
    )
}

fun KullaniciDto.toKullaniciBilgiModel(): KullaniciBilgiModel {
    return KullaniciBilgiModel(
        kullaniciAdi = this.username,
        ad = this.ad,
        soyad = this.soyad,
        adSoyad = this.ad + " " + this.soyad,
        eposta = this.eposta,
        resim = this.resim,
        dogumTarihi = this.dogumTarihi.convertDate2String(),
        ilgiAlanList = this.ilgiAlanlari.map {
            it.toKullaniciIlgiAlan()
        },
        cinsiyet = this.cinsiyet,
        isHaberdar = this.haberdarmi
    )
}
