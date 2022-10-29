package com.mesutemre.kutuphanem.parameter.ekleme.domain

import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import com.mesutemre.kutuphanem_base.use_case.IServiceCall
import com.mesutemre.kutuphanem_base.use_case.ServiceCallUseCase
import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem.model.ResponseStatusModel
import com.mesutemre.kutuphanem.parameter.ekleme.presentation.components.SelectedParameterType
import com.mesutemre.kutuphanem.parameter.kitaptur.data.remote.dto.KitapTurDto
import com.mesutemre.kutuphanem.parameter.kitaptur.data.repository.KitapTurRepository
import com.mesutemre.kutuphanem.parameter.yayinevi.data.remote.dto.YayinEviDto
import com.mesutemre.kutuphanem.parameter.yayinevi.domain.repository.YayinEviRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 10.07.2022
 */
class SaveParametreUseCase @Inject constructor(
    private val kitapTurRepository: KitapTurRepository,
    private val yayinEviRepository: YayinEviRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : IServiceCall by ServiceCallUseCase() {

    operator fun invoke(
        selectedParameterType: SelectedParameterType,
        parametreText: String
    ): Flow<BaseResourceEvent<ResponseStatusModel?>> {
        if (selectedParameterType == SelectedParameterType.YAYINEVI) {
            val yayinEviDto: YayinEviDto = YayinEviDto(
                aciklama = parametreText
            )
            return serviceCall {
                yayinEviRepository.yayinEviKaydet(yayinEviDto)
            }.flowOn(ioDispatcher)
        } else {
            val kitapTurDto: KitapTurDto = KitapTurDto(
                aciklama = parametreText
            )
            return serviceCall {
                kitapTurRepository.kitapTurKaydet(kitapTurDto)
            }.flowOn(ioDispatcher)
        }
    }
}