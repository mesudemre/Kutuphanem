package com.mesutemre.kutuphanem.dashboard_search.domain.use_case

import com.mesutemre.kutuphanem.dashboard_search.data.repository.SearchDashboardRepository
import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import com.mesutemre.kutuphanem_base.use_case.DbCallUseCase
import com.mesutemre.kutuphanem_base.use_case.IDbCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 15.10.2022
 */
class ClearSearchHistoryUseCase @Inject constructor(
    private val searchDashboardRepository: SearchDashboardRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : IDbCall by DbCallUseCase() {

    operator fun invoke(): Flow<BaseResourceEvent<Unit>> {
        return dbCall {
            searchDashboardRepository.clearSearchHistory()
        }.flowOn(ioDispatcher)
    }
}