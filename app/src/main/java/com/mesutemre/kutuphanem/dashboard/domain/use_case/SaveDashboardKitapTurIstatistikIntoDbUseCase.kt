package com.mesutemre.kutuphanem.dashboard.domain.use_case

import com.mesutemre.kutuphanem.dashboard.data.dao.entity.KitapTurIstatistikEntity
import com.mesutemre.kutuphanem.dashboard.data.repository.DashBoardRepository
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
 * @Date: 25.09.2022
 */
class SaveDashboardKitapTurIstatistikIntoDbUseCase @Inject constructor(
    private val dashBoardRepository: DashBoardRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): IDbCall by DbCallUseCase() {

    operator fun invoke(entityList: List<KitapTurIstatistikEntity>): Flow<BaseResourceEvent<Unit>> {
        return dbCall {
            dashBoardRepository.saveKitapTurIstatistikList(*entityList.toTypedArray())
        }.flowOn(ioDispatcher)
    }
}