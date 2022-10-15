package com.mesutemre.kutuphanem.dashboard_search.domain.use_case

import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.base.DbCallUseCase
import com.mesutemre.kutuphanem.base.IDbCall
import com.mesutemre.kutuphanem.dashboard_search.data.dao.entity.DashBoardSearchHistoryEntity
import com.mesutemre.kutuphanem.dashboard_search.data.repository.SearchDashboardRepository
import com.mesutemre.kutuphanem.dashboard_search.domain.model.KitapSearchItem
import com.mesutemre.kutuphanem.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 15.10.2022
 */
class SaveSearchHistoryItemToDbUseCase @Inject constructor(
    private val searchDashboardRepository: SearchDashboardRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : IDbCall by DbCallUseCase() {

    operator suspend fun invoke(item: KitapSearchItem): Flow<BaseResourceEvent<Unit>> {
        var isKitapMevcut = false
        val historyList = dbCall {
            searchDashboardRepository.getSearchHistoryList()
        }.flowOn(ioDispatcher)
        historyList.collectLatest {
            if (it is BaseResourceEvent.Success && it.data != null) {
                val kitapMevcut: () -> Boolean = { ->
                    it.data.any {
                        it.kitapId == item.kitapId
                    }
                }
                isKitapMevcut = kitapMevcut()
            }
        }
        if (!isKitapMevcut) {
            return dbCall {
                searchDashboardRepository.saveSearchHistoryToDb(
                    *listOf(
                        DashBoardSearchHistoryEntity(
                            kitapId = item.kitapId,
                            kitapAd = item.kitapAd,
                            yazarAd = item.yazarAd
                        )
                    ).toTypedArray()
                )
            }.flowOn(ioDispatcher)
        }
        return flow {
        }
    }
}