package com.mesutemre.kutuphanem.dashboard.domain.use_case

import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.base.BaseUseCase
import com.mesutemre.kutuphanem.dashboard.domain.model.DashboardKullaniciBilgiData
import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem.profile.data.remote.dto.toDashBoardKullaniciBilgi
import com.mesutemre.kutuphanem.profile.data.repository.KullaniciRepository
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import com.mesutemre.kutuphanem.util.KULLANICI_DB_MEVCUT
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 28.08.2022
 */
class GetDasboardKullaniciBilgi @Inject constructor(
    private val kullaniciRepository: KullaniciRepository,
    private val customSharedPreferences: CustomSharedPreferences,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): BaseUseCase() {

    operator fun invoke(): Flow<BaseResourceEvent<DashboardKullaniciBilgiData>> = flow{
        emit(BaseResourceEvent.Loading())
        val isDbKayit = customSharedPreferences.getBooleanFromSharedPreferences(
            KULLANICI_DB_MEVCUT
        )
        if (!isDbKayit) {
            val dashBoardUser = nonFlowServiceCall(ioDispatcher) {
                kullaniciRepository.getKullaniciBilgiByAPI()
            }
            if (dashBoardUser is BaseResourceEvent.Success) {
                emit(BaseResourceEvent.Success(
                    data = dashBoardUser.data?.toDashBoardKullaniciBilgi()!!
                ))
            }else if (dashBoardUser is BaseResourceEvent.Error) {
                emit(BaseResourceEvent.Error(message = dashBoardUser.message))
            }
        }else {

        }
    }
}