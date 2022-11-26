package com.mesutemre.kutuphanem.kitap_detay.domain.use_case

import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem.kitap_detay.data.dao.entity.convertKitapWithYayinEviKitapTurToKitapDetayItem
import com.mesutemre.kutuphanem.kitap_detay.data.repository.KitapDetayRepository
import com.mesutemre.kutuphanem.kitap_detay.domain.model.KitapDetayItem
import com.mesutemre.kutuphanem.kitap_liste.data.remote.dto.convertKitapDtoToKitapDetayItem
import com.mesutemre.kutuphanem.util.convertRersourceEventType
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import com.mesutemre.kutuphanem_base.use_case.DbCallUseCase
import com.mesutemre.kutuphanem_base.use_case.IDbCall
import com.mesutemre.kutuphanem_base.use_case.IServiceCall
import com.mesutemre.kutuphanem_base.use_case.ServiceCallUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 26.11.2022
 */
class GetKitapDetayByIdUseCase @Inject constructor(
    private val kitapDetayRepository: KitapDetayRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : IServiceCall by ServiceCallUseCase(), IDbCall by DbCallUseCase() {

    operator fun invoke(
        kitapId: Int,
        isFromArsiv: Boolean
    ): Flow<BaseResourceEvent<KitapDetayItem>> {
        if (!isFromArsiv) {
            return serviceCall {
                kitapDetayRepository.getKitapFromAPIById(kitapId)
            }.map {
                it.convertRersourceEventType {
                    it.data!!.first().convertKitapDtoToKitapDetayItem()
                }
            }
                .flowOn(ioDispatcher)
        } else {
            return dbCall {
                kitapDetayRepository.getKitapFromDbById(kitapId)
            }.map {
                it.convertRersourceEventType {
                    it.data!!.convertKitapWithYayinEviKitapTurToKitapDetayItem()
                }
            }
                .flowOn(ioDispatcher)
        }
    }
}