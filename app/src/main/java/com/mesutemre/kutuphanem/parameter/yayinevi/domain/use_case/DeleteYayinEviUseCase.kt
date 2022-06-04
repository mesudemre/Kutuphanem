package com.mesutemre.kutuphanem.parameter.yayinevi.domain.use_case

import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.base.BaseUseCase
import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem.model.ResponseStatusModel
import com.mesutemre.kutuphanem.parameter.yayinevi.data.remote.dto.YayinEviDto
import com.mesutemre.kutuphanem.parameter.yayinevi.data.remote.dto.YayinEviDurum
import com.mesutemre.kutuphanem.parameter.yayinevi.domain.model.YayinEviItem
import com.mesutemre.kutuphanem.parameter.yayinevi.domain.repository.YayinEviRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 4.06.2022
 */
class DeleteYayinEviUseCase @Inject constructor(
    private val yayinEviRepository: YayinEviRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
):BaseUseCase() {
    operator fun invoke(yayinEviItem: YayinEviItem): Flow<BaseResourceEvent<ResponseStatusModel?>> {
        val yayinEviDto: YayinEviDto = YayinEviDto(
            id = yayinEviItem.id,
            aciklama = yayinEviItem.aciklama,
            durum = YayinEviDurum.PASIF
        )
        return serviceCall {
            yayinEviRepository.yayinEviKaydet(yayinEviDto)
        }.flowOn(ioDispatcher)
    }
}