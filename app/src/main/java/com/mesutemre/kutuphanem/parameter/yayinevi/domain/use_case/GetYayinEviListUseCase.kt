package com.mesutemre.kutuphanem.parameter.yayinevi.domain.use_case

import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem.parameter.yayinevi.data.dao.entity.toYayinEviItem
import com.mesutemre.kutuphanem.parameter.yayinevi.data.remote.dto.toYayinEviItem
import com.mesutemre.kutuphanem.parameter.yayinevi.domain.model.YayinEviItem
import com.mesutemre.kutuphanem.parameter.yayinevi.domain.repository.YayinEviRepository
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import com.mesutemre.kutuphanem.util.PARAM_YAYINEVI_DB_KEY
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
 * @Date: 15.05.2022
 */
class GetYayinEviListUseCase @Inject constructor(
    private val yayinEviRepository: YayinEviRepository,
    private val customSharedPreferences: CustomSharedPreferences,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val storeYayinEviParametre: StoreYayinEviParametre
): IServiceCall by ServiceCallUseCase(), IDbCall by DbCallUseCase() {

    operator suspend fun invoke(isSwipeRefresh: Boolean): Flow<BaseResourceEvent<List<YayinEviItem>>> {
        val isDbKayit = customSharedPreferences.getBooleanFromSharedPreferences(PARAM_YAYINEVI_DB_KEY)
        if (isSwipeRefresh || !isDbKayit) {
            val serviceListEvent = serviceCall {
                yayinEviRepository.getYayinEviLisetByAPI()
            }.map {
                it.convertRersourceEventType{
                    it.data!!.map {k->
                        k.toYayinEviItem()
                    }
                }
            }.flowOn(ioDispatcher)
            serviceListEvent.collectLatest {
                if (it is BaseResourceEvent.Success) {
                    storeYayinEviParametre(it.data!!)
                }
            }
            return serviceListEvent
        }else {
            return dbCall {
                yayinEviRepository.getYayinEviListeByDAO()
            }.map {
                it.convertRersourceEventType {
                    it.data!!.map {k->
                        k.toYayinEviItem()
                    }
                }
            }.flowOn(ioDispatcher)
        }
    }
}