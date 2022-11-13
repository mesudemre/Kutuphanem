package com.mesutemre.kutuphanem.parameter.kitaptur.domain.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.mesutemre.kutuphanem.model.ResponseStatusModel
import com.mesutemre.kutuphanem.parameter.kitaptur.data.dao.IKitapTurDao
import com.mesutemre.kutuphanem.parameter.kitaptur.data.dao.entity.KitapTurEntity
import com.mesutemre.kutuphanem.parameter.kitaptur.data.remote.dto.IKitapTurApi
import com.mesutemre.kutuphanem.parameter.kitaptur.data.remote.dto.KitapTurDto
import com.mesutemre.kutuphanem.parameter.kitaptur.data.repository.KitapTurRepository
import com.mesutemre.kutuphanem.util.readBoolean
import com.mesutemre.kutuphanem.util.removeFromDataStore
import com.mesutemre.kutuphanem.util.saveData
import kotlinx.coroutines.flow.first
import retrofit2.Response
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 3.07.2022
 */
class KitapTurRepositoryImpl @Inject constructor(
    private val service: IKitapTurApi,
    private val kitapTurDao: IKitapTurDao,
    private val dataStore: DataStore<Preferences>
) : KitapTurRepository {

    override suspend fun getKitapTurListeByAPI(): Response<List<KitapTurDto>> {
        return service.getKitapTurListe()
    }

    override suspend fun getKitapTurListeByDAO(): List<KitapTurEntity> {
        return kitapTurDao.getKitapTurListe()
    }

    override suspend fun saveKitapTurList(vararg kitapTur: KitapTurEntity) {
        kitapTurDao.kitapTurKaydet(*kitapTur)
    }

    override suspend fun deleteKitapTurList() {
        kitapTurDao.deleteKitapTurList()
    }

    override suspend fun kitapTurKaydet(kitapTurDto: KitapTurDto): Response<ResponseStatusModel> {
        return service.kitapTurKaydet(kitapTurDto)
    }

    override suspend fun checkKitapTurDbKayit(key: String): Boolean {
        return dataStore.readBoolean(key).first()
    }

    override suspend fun saveKitapTurDbKayitToDataStore(key: String, value: Boolean) {
        dataStore.saveData(key, value)
    }

    override suspend fun clearKitapTurDbKayitCache(key: String) {
        dataStore.removeFromDataStore<String>(key)
    }
}