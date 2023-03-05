package com.mesutemre.kutuphanem.profile.domain.model

import com.mesutemre.kutuphanem.profile.data.remote.dto.CinsiyetDto

data class KullaniciBilgiModel(
    val kullaniciAdi: String,
    val ad: String,
    val soyad: String,
    val adSoyad: String,
    val eposta: String,
    val cinsiyet: CinsiyetDto,
    val isHaberdar: Boolean,
    val resim: String,
    val dogumTarihi: String,
    val ilgiAlanList: List<KullaniciIlgiAlanModel>
)
