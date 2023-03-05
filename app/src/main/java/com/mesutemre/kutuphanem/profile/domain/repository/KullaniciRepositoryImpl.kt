package com.mesutemre.kutuphanem.profile.domain.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.mesutemre.kutuphanem.profile.data.dao.IKullaniciDao
import com.mesutemre.kutuphanem.profile.data.dao.entity.KullaniciBilgiCinsiyetEntity
import com.mesutemre.kutuphanem.profile.data.dao.entity.KullaniciBilgiEntity
import com.mesutemre.kutuphanem.profile.data.dao.entity.KullaniciCinsiyetEntity
import com.mesutemre.kutuphanem.profile.data.dao.entity.KullaniciIlgiAlanEntity
import com.mesutemre.kutuphanem.profile.data.remote.IKullaniciApi
import com.mesutemre.kutuphanem.profile.data.remote.dto.KullaniciDto
import com.mesutemre.kutuphanem.profile.data.repository.KullaniciRepository
import com.mesutemre.kutuphanem.util.readBoolean
import com.mesutemre.kutuphanem.util.readString
import com.mesutemre.kutuphanem.util.saveData
import kotlinx.coroutines.flow.first
import retrofit2.Response
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 28.08.2022
 */
class KullaniciRepositoryImpl @Inject constructor(
    private val service: IKullaniciApi,
    private val dataStore: DataStore<Preferences>,
    private val kullaniciDao: IKullaniciDao
) : KullaniciRepository {

    override suspend fun getKullaniciBilgiByAPI(): Response<KullaniciDto> {
        return service.getKullaniciBilgi()
    }

    override suspend fun checkKullaniciDbMevcut(key: String): Boolean {
        return dataStore.readBoolean(key, false).first()
    }

    override suspend fun getKullaniciBilgiByKullaniciAdi(kullaniciAd: String): Map<KullaniciBilgiCinsiyetEntity, List<KullaniciIlgiAlanEntity>> {
        return kullaniciDao.getKullaniciBilgiByKullaniciAdi(kullaniciAd = kullaniciAd)
    }

    override suspend fun getKullaniciFromDataStore(key: String): String {
        return dataStore.readString(key, "").first()
    }

    override suspend fun saveKullaniciBilgi(vararg kullaniciBilgiEntity: KullaniciBilgiEntity) {
        return kullaniciDao.kullaniciBilgiKaydet(*kullaniciBilgiEntity)
    }

    override suspend fun saveKullaniciCinsiyetBilgi(vararg kullaniciCinsiyet: KullaniciCinsiyetEntity) {
        kullaniciDao.cinsiyetKaydet(*kullaniciCinsiyet)
    }

    override suspend fun saveKullaniciIlgiAlanlar(vararg kullaniciIlgiAlan: KullaniciIlgiAlanEntity) {
        kullaniciDao.kullaniciIlgiAlanKaydet(*kullaniciIlgiAlan)
    }

    override suspend fun saveKullaniciMevcutInDb(key: String) {
        dataStore.saveData(key, true)
    }
}