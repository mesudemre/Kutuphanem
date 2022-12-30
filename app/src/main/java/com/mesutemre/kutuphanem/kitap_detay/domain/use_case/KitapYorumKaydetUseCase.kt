package com.mesutemre.kutuphanem.kitap_detay.domain.use_case

import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem.kitap_detay.data.repository.KitapDetayRepository
import com.mesutemre.kutuphanem.kitap_detay.domain.model.KitapYorumKaydetModel
import com.mesutemre.kutuphanem.model.ResponseStatusModel
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import com.mesutemre.kutuphanem_base.use_case.IServiceCall
import com.mesutemre.kutuphanem_base.use_case.ServiceCallUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class KitapYorumKaydetUseCase @Inject constructor(
    private val kitapDetayRepository: KitapDetayRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : IServiceCall by ServiceCallUseCase() {

    operator fun invoke(kitapYorumKaydetModel: KitapYorumKaydetModel): Flow<BaseResourceEvent<ResponseStatusModel?>> {
        return serviceCall {
            kitapDetayRepository.kitapYorumKaydet(kitapYorumKaydetModel)
        }
    }
}