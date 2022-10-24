package com.mesutemre.kutuphanem.dashboard.domain.use_case

import com.mesutemre.kutuphanem.dashboard.domain.model.DashboardKullaniciBilgiData
import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem.profile.data.remote.dto.toDashBoardKullaniciBilgi
import com.mesutemre.kutuphanem.profile.data.repository.KullaniciRepository
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import com.mesutemre.kutuphanem.util.convertRersourceEventType
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import com.mesutemre.kutuphanem_base.use_case.IServiceCall
import com.mesutemre.kutuphanem_base.use_case.ServiceCallUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 28.08.2022
 */
class GetDasboardKullaniciBilgi @Inject constructor(
    private val kullaniciRepository: KullaniciRepository,
    private val customSharedPreferences: CustomSharedPreferences,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): IServiceCall by ServiceCallUseCase() {

    operator fun invoke(): Flow<BaseResourceEvent<DashboardKullaniciBilgiData>>{
        return serviceCall {
            kullaniciRepository.getKullaniciBilgiByAPI()
        }.map {
            it.convertRersourceEventType{
                it.data!!.toDashBoardKullaniciBilgi()
            }
        }.flowOn(ioDispatcher)
    }
}