package com.mesutemre.kutuphanem.parameter.kitaptur.domain.use_case

import com.mesutemre.kutuphanem.base.*
import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem.parameter.kitaptur.data.dao.entity.toKitapTurItem
import com.mesutemre.kutuphanem.parameter.kitaptur.data.remote.dto.toKitapTurItem
import com.mesutemre.kutuphanem.parameter.kitaptur.data.repository.KitapTurRepository
import com.mesutemre.kutuphanem.parameter.kitaptur.domain.model.KitapTurItem
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import com.mesutemre.kutuphanem.util.PARAM_KITAPTUR_DB_KEY
import com.mesutemre.kutuphanem.util.convertRersourceEventType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
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
): IServiceCall by ServiceCallUseCase(), IDbCall by DbCallUseCase() {

    operator fun invoke(isSwipeRefresh: Boolean) : Flow<BaseResourceEvent<List<KitapTurItem>>> {
        val isDbKayit = customSharedPreferences.getBooleanFromSharedPreferences(
            PARAM_KITAPTUR_DB_KEY
        )
        return if (isSwipeRefresh || !isDbKayit) {
            serviceCall {
                kitapTurRepository.getKitapTurListeByAPI()
            }.map {
                it.convertRersourceEventType(
                    onSuccess = {
                        storeKitapTurParametre(it.data!!)
                    }
                ) {
                    it.data!!.map {k->
                        k.toKitapTurItem()
                    }
                }
            }.flowOn(ioDispatcher)
        }else {
            dbCall {
                kitapTurRepository.getKitapTurListeByDAO()
            }.map {
                it.convertRersourceEventType {
                    it.data!!.map {k->
                        k.toKitapTurItem()
                    }
                }
            }.flowOn(ioDispatcher)
        }
    }
}