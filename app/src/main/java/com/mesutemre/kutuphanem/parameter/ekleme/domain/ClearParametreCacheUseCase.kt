package com.mesutemre.kutuphanem.parameter.ekleme.domain

import com.mesutemre.kutuphanem.parameter.ekleme.presentation.components.SelectedParameterType
import com.mesutemre.kutuphanem.parameter.kitaptur.data.repository.KitapTurRepository
import com.mesutemre.kutuphanem.parameter.yayinevi.domain.repository.YayinEviRepository
import com.mesutemre.kutuphanem.util.PARAM_KITAPTUR_DB_KEY
import com.mesutemre.kutuphanem.util.PARAM_YAYINEVI_DB_KEY
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 12.07.2022
 */
class ClearParametreCacheUseCase @Inject constructor(
    private val yayinEviRepository: YayinEviRepository,
    private val kitapTurRepository: KitapTurRepository
) {
    suspend operator fun invoke(type: SelectedParameterType) {
        if (type == SelectedParameterType.YAYINEVI)
            yayinEviRepository.clearYayinEviDbKayitCache(PARAM_YAYINEVI_DB_KEY)
        else
            kitapTurRepository.clearKitapTurDbKayitCache(PARAM_KITAPTUR_DB_KEY)
    }
}