package com.mesutemre.kutuphanem.dashboard_search.domain.repository

import com.mesutemre.kutuphanem.dashboard_search.data.dao.IDashBoardSearchHistoryDao
import com.mesutemre.kutuphanem.dashboard_search.data.dao.entity.DashBoardSearchHistoryEntity
import com.mesutemre.kutuphanem.dashboard_search.data.repository.SearchDashboardRepository
import com.mesutemre.kutuphanem.dashboard_search.domain.model.KitapSearchItem
import com.mesutemre.kutuphanem.kitap.data.remote.IKitapApi
import com.mesutemre.kutuphanem.kitap.data.remote.dto.KitapDto
import com.mesutemre.kutuphanem.parameter.kitaptur.data.dao.entity.KitapTurEntity
import retrofit2.Response
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 8.10.2022
 */
class SearchDashBoardRepositoryImpl @Inject constructor(
    private val kitapService: IKitapApi,
    private val dao: IDashBoardSearchHistoryDao
): SearchDashboardRepository {

    override suspend fun getSearchHistoryList(): List<DashBoardSearchHistoryEntity> {
        return dao.getSearchHistoryList()
    }

    override suspend fun getKitapSearchList(kitap: KitapDto): Response<List<KitapDto>> {
        return kitapService.getKitapListe(kitap)
    }

    override suspend fun saveSearchHistoryToDb(vararg searchHistory: DashBoardSearchHistoryEntity) {
        dao.searchHistoryKaydet(*searchHistory)
    }

    override suspend fun clearSearchHistory() {
        dao.deleteSearchHistory()
    }
}