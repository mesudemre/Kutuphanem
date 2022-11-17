package com.mesutemre.kutuphanem.kitap_liste.domain.use_case

import com.mesutemre.kutuphanem.kitap_liste.data.remote.dto.KitapDto
import com.mesutemre.kutuphanem.kitap_liste.data.repository.KitapListeRepository
import com.mesutemre.kutuphanem.model.ResponseStatusModel
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import com.mesutemre.kutuphanem_base.use_case.IServiceCall
import com.mesutemre.kutuphanem_base.use_case.ServiceCallUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 17.11.2022
 */
class KitapBegenUseCase @Inject constructor(
    private val kitapRepository: KitapListeRepository
) : IServiceCall by ServiceCallUseCase() {

    operator fun invoke(kitapId: Int): Flow<BaseResourceEvent<ResponseStatusModel?>> {
        return serviceCall {
            kitapRepository.kitapBegen(
                KitapDto(
                    id = kitapId
                )
            )
        }
    }
}