package com.mesutemre.kutuphanem.dashboard.domain.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.mesutemre.kutuphanem.dashboard.data.dao.entity.IDashBoardDao
import com.mesutemre.kutuphanem.dashboard.data.dao.entity.KitapTurIstatistikEntity
import com.mesutemre.kutuphanem.dashboard.data.dao.entity.KitapYilIstatistikEntity
import com.mesutemre.kutuphanem.dashboard.data.remote.IDashBoardApi
import com.mesutemre.kutuphanem.dashboard.data.remote.dto.KitapTurIstatistikDto
import com.mesutemre.kutuphanem.dashboard.data.remote.dto.KitapYilIstatistikDto
import com.mesutemre.kutuphanem.dashboard.data.repository.DashBoardRepository
import com.mesutemre.kutuphanem.util.readBoolean
import com.mesutemre.kutuphanem.util.saveData
import kotlinx.coroutines.flow.first
import retrofit2.Response
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 17.09.2022
 */
class DashBoardRepositoryImpl @Inject constructor(
    private val service: IDashBoardApi,
    private val dao: IDashBoardDao,
    private val dataStore: DataStore<Preferences>
) : DashBoardRepository {

    override suspend fun getKitapTurIstatistikByAPI(): Response<List<KitapTurIstatistikDto>> {
        return service.getKitapTurIstatistikListe()
    }

    override suspend fun getKitapTurIstatistikByDAO(): List<KitapTurIstatistikEntity> {
        return dao.getKitapTurIstatistikListe()
    }

    override suspend fun saveKitapTurIstatistikList(vararg kitapTur: KitapTurIstatistikEntity) {
        dao.kitapTurIstatistikKaydet(*kitapTur)
    }

    override suspend fun deleteKitapTurIstatistikList() {
        dao.deleteKitapTurIstatistikList()
    }

    override suspend fun getKitapYilIstatistikByAPI(): Response<List<KitapYilIstatistikDto>> {
        return service.getKitapYilIstatistikListe()
    }

    override suspend fun getKitapYilIstatistikByDAO(): List<KitapYilIstatistikEntity> {
        return dao.getKitapYilIstatistikListe()
    }

    override suspend fun saveKitapYilIstatistikList(vararg kitapYil: KitapYilIstatistikEntity) {
        dao.kitapYilIstatistikKaydet(*kitapYil)
    }

    override suspend fun deleteKitapYilIstatistikList() {
        dao.deleteKitapYilIstatistikList()
    }

    override suspend fun checkKitapTurIstatistikKayit(key: String): Boolean {
        return dataStore.readBoolean(key).first()
    }

    override suspend fun saveKitapTurIstatistikDbKayitToDataStore(
        key: String,
        value: Boolean
    ) {
        dataStore.saveData(key, value)
    }
}