package com.mesutemre.kutuphanem.parameter.yayinevi.domain.use_case

import com.mesutemre.kutuphanem.base.*
import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem.parameter.yayinevi.data.dao.entity.toYayinEviItem
import com.mesutemre.kutuphanem.parameter.yayinevi.data.remote.dto.toYayinEviItem
import com.mesutemre.kutuphanem.parameter.yayinevi.domain.model.YayinEviItem
import com.mesutemre.kutuphanem.parameter.yayinevi.domain.repository.YayinEviRepository
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import com.mesutemre.kutuphanem.util.PARAM_YAYINEVI_DB_KEY
import com.mesutemre.kutuphanem.util.convertRersourceEventType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
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
): IServiceCall by ServiceCallUseCase(), IDbCall by DbCallUseCase(){

    operator fun invoke(isSwipeRefresh: Boolean): Flow<BaseResourceEvent<List<YayinEviItem>>> {
        val isDbKayit = customSharedPreferences.getBooleanFromSharedPreferences(PARAM_YAYINEVI_DB_KEY)
        return if (isSwipeRefresh || !isDbKayit) {
            serviceCall {
                yayinEviRepository.getYayinEviLisetByAPI()
            }.map {
                it.convertRersourceEventType(
                    onSuccess = {
                        storeYayinEviParametre(it.data!!)
                    }
                ) {
                    it.data!!.map {k->
                        k.toYayinEviItem()
                    }
                }
            }.flowOn(ioDispatcher)
        }else {
            dbCall {
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