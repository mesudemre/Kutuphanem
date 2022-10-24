package com.mesutemre.kutuphanem.dashboard.domain.use_case

import com.mesutemre.kutuphanem.dashboard.data.dao.entity.convertToDashBoardKitapYilIstatistikItem
import com.mesutemre.kutuphanem.dashboard.data.remote.dto.KitapYilIstatistikDto
import com.mesutemre.kutuphanem.dashboard.data.remote.dto.convertToDashBoardKitapYilIstatistikItem
import com.mesutemre.kutuphanem.dashboard.data.repository.DashBoardRepository
import com.mesutemre.kutuphanem.dashboard.domain.model.DashBoardKitapYilIstatistikItem
import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import com.mesutemre.kutuphanem.util.DASHBOARD_YIL_ISTATISTIK
import com.mesutemre.kutuphanem.util.convertRersourceEventType
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import com.mesutemre.kutuphanem_base.use_case.DbCallUseCase
import com.mesutemre.kutuphanem_base.use_case.IDbCall
import com.mesutemre.kutuphanem_base.use_case.IServiceCall
import com.mesutemre.kutuphanem_base.use_case.ServiceCallUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 2.10.2022
 */
class GetKitapYilIstatistik @Inject constructor(
    private val dashBoardRepository: DashBoardRepository,
    private val customSharedPreferences: CustomSharedPreferences,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val storeDashboardKitapYilIstatistik: StoreDashboardKitapYilIstatistik
) : IServiceCall by ServiceCallUseCase(), IDbCall by DbCallUseCase() {

    operator suspend fun invoke(): Flow<BaseResourceEvent<List<DashBoardKitapYilIstatistikItem>>> {
        val isDbKayit = customSharedPreferences.getBooleanFromSharedPreferences(
            DASHBOARD_YIL_ISTATISTIK
        )
        if (!isDbKayit) {
            var kitapYilIstatistikList: List<KitapYilIstatistikDto>? = null
            val serviceEvent = serviceCall {
                dashBoardRepository.getKitapYilIstatistikByAPI()
            }.map {
                kitapYilIstatistikList = it.data
                it.convertRersourceEventType {
                    it.data!!.map { k ->
                        k.convertToDashBoardKitapYilIstatistikItem()
                    }
                }
            }.flowOn(ioDispatcher)
            serviceEvent.collectLatest {
                if (it is BaseResourceEvent.Success) {
                    kitapYilIstatistikList?.let {
                        storeDashboardKitapYilIstatistik(it)
                    }
                }
            }
            return serviceEvent
        }else {
            return dbCall {
                dashBoardRepository.getKitapYilIstatistikByDAO()
            }.map {
                it.convertRersourceEventType {
                    it.data!!.map { ist->
                        ist.convertToDashBoardKitapYilIstatistikItem()
                    }
                }
            }
        }
    }
}