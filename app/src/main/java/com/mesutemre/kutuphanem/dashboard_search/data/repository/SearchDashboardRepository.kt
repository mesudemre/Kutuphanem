package com.mesutemre.kutuphanem.dashboard_search.data.repository

import com.mesutemre.kutuphanem.dashboard_search.data.dao.entity.DashBoardSearchHistoryEntity
import com.mesutemre.kutuphanem.kitap.data.remote.dto.KitapDto
import com.mesutemre.kutuphanem.parameter.kitaptur.data.dao.entity.KitapTurEntity
import retrofit2.Response

interface SearchDashboardRepository {

    suspend fun getSearchHistoryList(): List<DashBoardSearchHistoryEntity>

    suspend fun getKitapSearchList(kitap: KitapDto): Response<List<KitapDto>>

    suspend fun saveSearchHistoryToDb(vararg searchHistory: DashBoardSearchHistoryEntity)

    suspend fun clearSearchHistory()
}