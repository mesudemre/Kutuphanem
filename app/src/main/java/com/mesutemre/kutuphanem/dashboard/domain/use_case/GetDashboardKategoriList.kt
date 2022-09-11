package com.mesutemre.kutuphanem.dashboard.domain.use_case

import com.mesutemre.kutuphanem.base.*
import com.mesutemre.kutuphanem.dashboard.domain.model.DashboardKategoriItem
import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem.parameter.kitaptur.data.dao.entity.toDashboardKategoriItem
import com.mesutemre.kutuphanem.parameter.kitaptur.data.remote.dto.toDashboardKategoriItem
import com.mesutemre.kutuphanem.parameter.kitaptur.data.repository.KitapTurRepository
import com.mesutemre.kutuphanem.parameter.kitaptur.domain.use_case.StoreKitapTurParametre
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
 * @Date: 29.08.2022
 */
class GetDashboardKategoriList @Inject constructor(
    private val kitapTurRepository: KitapTurRepository,
    private val customSharedPreferences: CustomSharedPreferences,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val storeKitapTurParametre: StoreKitapTurParametre
): IServiceCall by ServiceCallUseCase(),IDbCall by DbCallUseCase() {

    operator fun invoke() : Flow<BaseResourceEvent<List<DashboardKategoriItem>>>{
        val isDbKayit = customSharedPreferences.getBooleanFromSharedPreferences(
            PARAM_KITAPTUR_DB_KEY
        )
        return if (!isDbKayit) {
           serviceCall {
                kitapTurRepository.getKitapTurListeByAPI()
            }.map {
               it.convertRersourceEventType {
                   it.data!!.map {k->
                       k.toDashboardKategoriItem()
                   }
               }
           }.flowOn(ioDispatcher)

        } else {
            dbCall {
                kitapTurRepository.getKitapTurListeByDAO()
            }.map {
                it.convertRersourceEventType {
                    it.data!!.map {k->
                        k.toDashboardKategoriItem()
                    }
                }
            }.flowOn(ioDispatcher)
        }
    }
}