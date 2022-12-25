package com.mesutemre.kutuphanem.kitap_detay.domain.use_case

import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem.kitap_detay.data.remote.dto.convertKitapYorumToKitapDetayBottomSheetYorum
import com.mesutemre.kutuphanem.kitap_detay.data.repository.KitapDetayRepository
import com.mesutemre.kutuphanem.kitap_detay.domain.model.KitapDetayBottomSheetYorumModel
import com.mesutemre.kutuphanem.util.convertRersourceEventType
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import com.mesutemre.kutuphanem_base.use_case.IServiceCall
import com.mesutemre.kutuphanem_base.use_case.ServiceCallUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetKitapYorumListeByKitapIdUseCase @Inject constructor(
    private val kitapDetayRepository: KitapDetayRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : IServiceCall by ServiceCallUseCase() {

    operator fun invoke(
        kitapId: Int
    ): Flow<BaseResourceEvent<List<KitapDetayBottomSheetYorumModel>>> {
        return serviceCall {
            kitapDetayRepository.getKitapYorumListeByKitapId(kitapId)
        }.map {
            it.convertRersourceEventType {
                it.data!!.yorumListe?.map { k ->
                    k.convertKitapYorumToKitapDetayBottomSheetYorum()
                } ?: emptyList()
            }
        }
            .flowOn(ioDispatcher)
    }
}