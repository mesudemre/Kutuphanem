package com.mesutemre.kutuphanem.parameter.yayinevi.domain.use_case

import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.base.BaseUseCase
import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem.parameter.yayinevi.data.dao.entity.toYayinEviItem
import com.mesutemre.kutuphanem.parameter.yayinevi.data.remote.dto.YayinEviDto
import com.mesutemre.kutuphanem.parameter.yayinevi.data.remote.dto.toYayinEviItem
import com.mesutemre.kutuphanem.parameter.yayinevi.domain.model.YayinEviItem
import com.mesutemre.kutuphanem.parameter.yayinevi.domain.repository.YayinEviRepository
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import com.mesutemre.kutuphanem.util.PARAM_YAYINEVI_DB_KEY
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 15.05.2022
 */
class GetYayinEviListUseCase @Inject constructor(
    private val yayinEviRepository: YayinEviRepository,
    private val customSharedPreferences: CustomSharedPreferences,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val storeYayinEviParametre: StoreYayinEviParametre
): BaseUseCase(){

    operator fun invoke(isSwipeRefresh: Boolean): Flow<BaseResourceEvent<List<YayinEviItem>>> = flow{
        emit(BaseResourceEvent.Loading())
        val isDbKayit = customSharedPreferences.getBooleanFromSharedPreferences(PARAM_YAYINEVI_DB_KEY)
        if (isSwipeRefresh || !isDbKayit) {
            val serviceList = nonFlowServiceCall(ioDispatcher) {
                yayinEviRepository.getYayinEviLisetByAPI()
            }
            if (serviceList is BaseResourceEvent.Success) {
                emit(BaseResourceEvent.Success(
                    data = serviceList.data?.map {
                        it.toYayinEviItem()
                    }!!
                ))
                storeYayinEviParametre(serviceList.data)
            }else if (serviceList is BaseResourceEvent.Error) {
                emit(BaseResourceEvent.Error(serviceList.message))
            }
        }else {
            val dbList = nonFlowDbCall(ioDispatcher) {
                yayinEviRepository.getYayinEviListeByDAO()
            }
            if (dbList is BaseResourceEvent.Success) {
                emit(BaseResourceEvent.Success(
                    data = dbList.data?.map {
                        it.toYayinEviItem()
                    }!!
                ))
            }else if (dbList is BaseResourceEvent.Error) {
                emit(BaseResourceEvent.Error(dbList.message))
            }
        }
    }
}