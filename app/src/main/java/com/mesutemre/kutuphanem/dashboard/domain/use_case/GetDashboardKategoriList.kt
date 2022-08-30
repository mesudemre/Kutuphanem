package com.mesutemre.kutuphanem.dashboard.domain.use_case

import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.base.BaseUseCase
import com.mesutemre.kutuphanem.dashboard.domain.model.DashboardKategoriItem
import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem.parameter.kitaptur.data.dao.entity.toDashboardKategoriItem
import com.mesutemre.kutuphanem.parameter.kitaptur.data.remote.dto.toDashboardKategoriItem
import com.mesutemre.kutuphanem.parameter.kitaptur.data.repository.KitapTurRepository
import com.mesutemre.kutuphanem.parameter.kitaptur.domain.use_case.StoreKitapTurParametre
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import com.mesutemre.kutuphanem.util.PARAM_KITAPTUR_DB_KEY
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 29.08.2022
 */
class GetDashboardKategoriList @Inject constructor(
    private val kitapTurRepository: KitapTurRepository,
    private val customSharedPreferences: CustomSharedPreferences,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val storeKitapTurParametre: StoreKitapTurParametre
): BaseUseCase() {

    operator fun invoke() : Flow<BaseResourceEvent<List<DashboardKategoriItem>>> = flow{
        emit(BaseResourceEvent.Loading())
        val isDbKayit = customSharedPreferences.getBooleanFromSharedPreferences(
            PARAM_KITAPTUR_DB_KEY
        )
        if (!isDbKayit) {
            val serviceList = nonFlowServiceCall(ioDispatcher) {
                kitapTurRepository.getKitapTurListeByAPI()
            }
            if (serviceList is BaseResourceEvent.Success) {
                emit(BaseResourceEvent.Success(
                    data = serviceList.data?.map {
                        it.toDashboardKategoriItem()
                    }!!
                ))
                storeKitapTurParametre(serviceList.data)
            }else if (serviceList is BaseResourceEvent.Error) {
                emit(BaseResourceEvent.Error(serviceList.message))
            }
        } else {
            val dbList = nonFlowDbCall(ioDispatcher) {
                kitapTurRepository.getKitapTurListeByDAO()
            }
            if (dbList is BaseResourceEvent.Success) {
                emit(BaseResourceEvent.Success(
                    data = dbList.data?.map {
                        it.toDashboardKategoriItem()
                    }!!
                ))
            }else if (dbList is BaseResourceEvent.Error) {
                emit(BaseResourceEvent.Error(dbList.message))
            }
        }
    }
}