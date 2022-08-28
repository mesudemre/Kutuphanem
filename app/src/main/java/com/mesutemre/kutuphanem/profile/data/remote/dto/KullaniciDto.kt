package com.mesutemre.kutuphanem.profile.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mesutemre.kutuphanem.dashboard.domain.model.DashboardKullaniciBilgiData
import com.mesutemre.kutuphanem.parameter.kitaptur.data.remote.dto.KitapTurDto
import java.util.*

data class KullaniciDto(

    @SerializedName("username")
    @Expose
    var username:String,

    @SerializedName("ad")
    @Expose
    var ad:String,

    @SerializedName("soyad")
    @Expose
    var soyad:String,

    @SerializedName("dogumTarihi")
    @Expose
    var dogumTarihi: Date,

    @SerializedName("resim")
    @Expose
    var resim:String,

    @SerializedName("eposta")
    @Expose
    var eposta:String,

    @SerializedName("cinsiyet")
    @Expose
    var cinsiyet: CinsiyetModel,

    @SerializedName("haberdarmi")
    @Expose
    var haberdarmi:Boolean,

    @SerializedName("ilgiAlanlari")
    @Expose
    var ilgiAlanlari:List<KitapTurDto>
)

fun KullaniciDto.toDashBoardKullaniciBilgi(): DashboardKullaniciBilgiData {
    return DashboardKullaniciBilgiData(
        adSoyad = this.ad+" "+this.soyad,
        resim = this.resim
    )
}
