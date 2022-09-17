package com.mesutemre.kutuphanem.dashboard.data.repository

import com.mesutemre.kutuphanem.dashboard.data.dao.entity.KitapTurIstatistikEntity
import com.mesutemre.kutuphanem.dashboard.data.remote.dto.KitapTurIstatistikDto
import retrofit2.Response

interface DashBoardRepository {

    suspend fun getKitapTurIstatistikByAPI(): Response<List<KitapTurIstatistikDto>>

    suspend fun getKitapTurIstatistikByDAO() :List<KitapTurIstatistikEntity>

    suspend fun saveKitapTurIstatistikList(vararg kitapTur: KitapTurIstatistikEntity)

    suspend fun deleteKitapTurIstatistikList()
}