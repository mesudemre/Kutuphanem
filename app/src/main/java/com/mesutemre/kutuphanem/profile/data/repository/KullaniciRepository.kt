package com.mesutemre.kutuphanem.profile.data.repository

import com.mesutemre.kutuphanem.profile.data.dao.entity.KullaniciBilgiCinsiyetEntity
import com.mesutemre.kutuphanem.profile.data.dao.entity.KullaniciBilgiEntity
import com.mesutemre.kutuphanem.profile.data.dao.entity.KullaniciCinsiyetEntity
import com.mesutemre.kutuphanem.profile.data.dao.entity.KullaniciIlgiAlanEntity
import com.mesutemre.kutuphanem.profile.data.remote.dto.KullaniciDto
import retrofit2.Response

interface KullaniciRepository {

    suspend fun getKullaniciBilgiByAPI(): Response<KullaniciDto>

    suspend fun checkKullaniciDbMevcut(key: String): Boolean

    suspend fun getKullaniciBilgiByKullaniciAdi(kullaniciAd: String): Map<KullaniciBilgiCinsiyetEntity, List<KullaniciIlgiAlanEntity>>

    suspend fun getKullaniciFromDataStore(key: String): String

    suspend fun saveKullaniciBilgi(vararg kullaniciBilgiEntity: KullaniciBilgiEntity)

    suspend fun saveKullaniciCinsiyetBilgi(vararg kullaniciCinsiyet: KullaniciCinsiyetEntity)

    suspend fun saveKullaniciIlgiAlanlar(vararg kullaniciIlgiAlan: KullaniciIlgiAlanEntity)

    suspend fun saveKullaniciMevcutInDb(key: String)
}