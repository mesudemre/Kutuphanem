package com.mesutemre.kutuphanem.parameter.kitaptur.domain.use_case

import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.base.BaseUseCase
import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem.parameter.kitaptur.data.dao.entity.toKitapTurItem
import com.mesutemre.kutuphanem.parameter.kitaptur.data.remote.dto.toKitapTurItem
import com.mesutemre.kutuphanem.parameter.kitaptur.data.repository.KitapTurRepository
import com.mesutemre.kutuphanem.parameter.kitaptur.domain.model.KitapTurItem
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import com.mesutemre.kutuphanem.util.PARAM_KITAPTUR_DB_KEY
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 3.07.2022
 */
class GetKitapTurListUseCase @Inject constructor(
    private val kitapTurRepository: KitapTurRepository,
    private val customSharedPreferences: CustomSharedPreferences,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val storeKitapTurParametre: StoreKitapTurParametre
): BaseUseCase() {

    operator fun invoke(isSwipeRefresh: Boolean) : Flow<BaseResourceEvent<List<KitapTurItem>>> = flow{
        emit(BaseResourceEvent.Loading())
        val isDbKayit = customSharedPreferences.getBooleanFromSharedPreferences(
            PARAM_KITAPTUR_DB_KEY
        )
        if (isSwipeRefresh || !isDbKayit) {
            val serviceList = nonFlowServiceCall(ioDispatcher) {
                kitapTurRepository.getKitapTurListeByAPI()
            }
            if (serviceList is BaseResourceEvent.Success) {
                emit(BaseResourceEvent.Success(
                    data = serviceList.data?.map {
                        it.toKitapTurItem()
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
                        it.toKitapTurItem()
                    }!!
                ))
            }else if (dbList is BaseResourceEvent.Error) {
                emit(BaseResourceEvent.Error(dbList.message))
            }
        }
    }
}