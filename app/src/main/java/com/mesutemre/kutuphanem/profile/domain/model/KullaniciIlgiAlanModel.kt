package com.mesutemre.kutuphanem.profile.domain.model

import com.mesutemre.kutuphanem.profile.data.dao.entity.KullaniciIlgiAlanEntity

data class KullaniciIlgiAlanModel(
    val id: Int,
    val ilgiAlan: String,
    val resim: String
)

fun KullaniciIlgiAlanModel.toKullaniciIlgiAlanEntity(kullaniciAd: String): KullaniciIlgiAlanEntity {
    return KullaniciIlgiAlanEntity(
        kullaniciAd = kullaniciAd,
        ilgiAlanId = this.id,
        ilgiAlan = this.ilgiAlan,
        ilgiAlanResim = this.resim
    )
}
