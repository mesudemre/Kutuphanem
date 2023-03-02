package com.mesutemre.kutuphanem.dashboard.domain.use_case

import com.mesutemre.kutuphanem.dashboard.data.remote.dto.convertToDashBoardKitapYilIstatistikItem
import com.mesutemre.kutuphanem.dashboard.data.repository.DashBoardRepository
import com.mesutemre.kutuphanem.dashboard.domain.model.DashBoardKitapYilIstatistikItem
import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem.util.convertRersourceEventType
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import com.mesutemre.kutuphanem_base.use_case.DbCallUseCase
import com.mesutemre.kutuphanem_base.use_case.IDbCall
import com.mesutemre.kutuphanem_base.use_case.IServiceCall
import com.mesutemre.kutuphanem_base.use_case.ServiceCallUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 2.10.2022
 */
class GetKitapYilIstatistik @Inject constructor(
    private val dashBoardRepository: DashBoardRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : IServiceCall by ServiceCallUseCase(), IDbCall by DbCallUseCase() {

    operator suspend fun invoke(): Flow<BaseResourceEvent<List<DashBoardKitapYilIstatistikItem>>> {
        return serviceCall {
            dashBoardRepository.getKitapYilIstatistikByAPI()
        }.map {
            it.convertRersourceEventType {
                it.data!!.map { k ->
                    k.convertToDashBoardKitapYilIstatistikItem()
                }
            }
        }.flowOn(ioDispatcher)
    }
}