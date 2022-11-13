package com.mesutemre.kutuphanem.dashboard.data.repository

import com.mesutemre.kutuphanem.dashboard.data.dao.entity.KitapTurIstatistikEntity
import com.mesutemre.kutuphanem.dashboard.data.dao.entity.KitapYilIstatistikEntity
import com.mesutemre.kutuphanem.dashboard.data.remote.dto.KitapTurIstatistikDto
import com.mesutemre.kutuphanem.dashboard.data.remote.dto.KitapYilIstatistikDto
import retrofit2.Response

interface DashBoardRepository {

    suspend fun getKitapTurIstatistikByAPI(): Response<List<KitapTurIstatistikDto>>

    suspend fun getKitapTurIstatistikByDAO(): List<KitapTurIstatistikEntity>

    suspend fun saveKitapTurIstatistikList(vararg kitapTur: KitapTurIstatistikEntity)

    suspend fun deleteKitapTurIstatistikList()

    suspend fun getKitapYilIstatistikByAPI(): Response<List<KitapYilIstatistikDto>>

    suspend fun getKitapYilIstatistikByDAO(): List<KitapYilIstatistikEntity>

    suspend fun saveKitapYilIstatistikList(vararg kitapYil: KitapYilIstatistikEntity)

    suspend fun deleteKitapYilIstatistikList()

    suspend fun checkKitapTurIstatistikKayit(key: String): Boolean

    suspend fun saveKitapTurIstatistikDbKayitToDataStore(key: String, value: Boolean)
}