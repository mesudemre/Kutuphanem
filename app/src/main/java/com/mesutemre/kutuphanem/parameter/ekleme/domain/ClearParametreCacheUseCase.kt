package com.mesutemre.kutuphanem.parameter.ekleme.domain

import com.mesutemre.kutuphanem.parameter.ekleme.presentation.components.SelectedParameterType
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import com.mesutemre.kutuphanem.util.PARAM_KITAPTUR_DB_KEY
import com.mesutemre.kutuphanem.util.PARAM_YAYINEVI_DB_KEY
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 12.07.2022
 */
class ClearParametreCacheUseCase @Inject constructor(
    private val customSharedPreferences: CustomSharedPreferences
) {
    operator fun invoke(type: SelectedParameterType) {
        if (type == SelectedParameterType.YAYINEVI)
            customSharedPreferences.removeFromSharedPreferences(PARAM_YAYINEVI_DB_KEY)
        else
            customSharedPreferences.removeFromSharedPreferences(PARAM_KITAPTUR_DB_KEY)
    }
}