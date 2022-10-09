package com.mesutemre.kutuphanem.dashboard.domain.use_case

import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.base.DbCallUseCase
import com.mesutemre.kutuphanem.base.IDbCall
import com.mesutemre.kutuphanem.dashboard.data.repository.DashBoardRepository
import com.mesutemre.kutuphanem.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 2.10.2022
 */
class DeleteDashboardKitapYilIstatistikFromDbUseCase @Inject constructor(
    private val dashBoardRepository: DashBoardRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): IDbCall by DbCallUseCase() {

    operator fun invoke() : Flow<BaseResourceEvent<Unit>> {
        return dbCall {
            dashBoardRepository.deleteKitapYilIstatistikList()
        }.flowOn(ioDispatcher)
    }
}