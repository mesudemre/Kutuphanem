package com.mesutemre.kutuphanem.parameter.yayinevi.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.mesutemre.kutuphanem.model.ResponseStatusModel
import com.mesutemre.kutuphanem.parameter.yayinevi.data.dao.entity.IYayinEviDao
import com.mesutemre.kutuphanem.parameter.yayinevi.data.dao.entity.YayinEviEntity
import com.mesutemre.kutuphanem.parameter.yayinevi.data.remote.dto.IYayinEviApi
import com.mesutemre.kutuphanem.parameter.yayinevi.data.remote.dto.YayinEviDto
import com.mesutemre.kutuphanem.parameter.yayinevi.domain.repository.YayinEviRepository
import com.mesutemre.kutuphanem.util.readBoolean
import com.mesutemre.kutuphanem.util.removeFromDataStore
import com.mesutemre.kutuphanem.util.saveData
import kotlinx.coroutines.flow.first
import retrofit2.Response
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 15.05.2022
 */
class YayinEviRepositoryImpl @Inject constructor(
    private val service: IYayinEviApi,
    private val dao: IYayinEviDao,
    private val dataStore: DataStore<Preferences>
) : YayinEviRepository {

    override suspend fun getYayinEviLisetByAPI(): Response<List<YayinEviDto>> {
        return service.getYayinEviListe()
    }

    override suspend fun getYayinEviListeByDAO(): List<YayinEviEntity> {
        return dao.getYayinEviListe()
    }

    override suspend fun saveYayinEviList(vararg yayinEvi: YayinEviEntity) {
        dao.yayinEviKaydet(*yayinEvi)
    }

    override suspend fun deleteYayinEviList() {
        dao.deleteYayinEviList()
    }

    override suspend fun yayinEviKaydet(yayinEviDto: YayinEviDto): Response<ResponseStatusModel> {
        return service.yayinEviKaydet(yayinEviDto)
    }

    override suspend fun checkYayinEviDbKayit(key: String): Boolean {
        return dataStore.readBoolean(key).first()
    }

    override suspend fun saveYayinEviDbKayitToDataStore(key: String, value: Boolean) {
        dataStore.saveData(key, value)
    }

    override suspend fun clearYayinEviDbKayitCache(key: String) {
        dataStore.removeFromDataStore<String>(key)
    }
}