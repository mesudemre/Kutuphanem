package com.mesutemre.kutuphanem.parameter.kitaptur.domain.use_case

import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.base.BaseUseCase
import com.mesutemre.kutuphanem.base.IServiceCall
import com.mesutemre.kutuphanem.base.ServiceCallUseCase
import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem.model.ResponseStatusModel
import com.mesutemre.kutuphanem.parameter.kitaptur.data.remote.dto.KitapTurDto
import com.mesutemre.kutuphanem.parameter.kitaptur.data.remote.dto.KitapTurDurum
import com.mesutemre.kutuphanem.parameter.kitaptur.data.repository.KitapTurRepository
import com.mesutemre.kutuphanem.parameter.kitaptur.domain.model.KitapTurItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 3.07.2022
 */
class DeleteKitapTurUseCase  @Inject constructor(
    private val kitapTurRepository: KitapTurRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): IServiceCall by ServiceCallUseCase() {
    operator fun invoke(kitapTurItem: KitapTurItem): Flow<BaseResourceEvent<ResponseStatusModel?>> {
        val kitapTurDto: KitapTurDto = KitapTurDto(
            id = kitapTurItem.id,
            aciklama = kitapTurItem.aciklama,
            durum = KitapTurDurum.PASIF,
            resim = ""
        )
        return serviceCall {
            kitapTurRepository.kitapTurKaydet(kitapTurDto)
        }.flowOn(ioDispatcher)
    }
}