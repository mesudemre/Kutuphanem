package com.mesutemre.kutuphanem.dashboard.domain.repository

import com.mesutemre.kutuphanem.dashboard.data.dao.entity.IDashBoardDao
import com.mesutemre.kutuphanem.dashboard.data.dao.entity.KitapTurIstatistikEntity
import com.mesutemre.kutuphanem.dashboard.data.remote.IDashBoardApi
import com.mesutemre.kutuphanem.dashboard.data.remote.dto.KitapTurIstatistikDto
import com.mesutemre.kutuphanem.dashboard.data.repository.DashBoardRepository
import retrofit2.Response
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 17.09.2022
 */
class DashBoardRepositoryImpl @Inject constructor(
    private val service: IDashBoardApi,
    private val dao: IDashBoardDao
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
}