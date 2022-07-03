package com.mesutemre.kutuphanem.parameter.kitaptur.domain.use_case

import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.base.BaseUseCase
import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem.parameter.kitaptur.data.dao.entity.KitapTurEntity
import com.mesutemre.kutuphanem.parameter.kitaptur.data.remote.dto.KitapTurDto
import com.mesutemre.kutuphanem.parameter.kitaptur.data.repository.KitapTurRepository
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import com.mesutemre.kutuphanem.util.PARAM_KITAPTUR_DB_KEY
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 3.07.2022
 */
class StoreKitapTurParametre @Inject constructor(
    private val kitapTurRepository: KitapTurRepository,
    private val customSharedPreferences: CustomSharedPreferences,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): BaseUseCase(){

    suspend operator fun invoke(list: List<KitapTurDto>) {
        val deleteEvent = nonFlowDbCall(ioDispatcher) {
            kitapTurRepository.deleteKitapTurList()
        }
        if (deleteEvent is BaseResourceEvent.Success) {
            val kitapTurEntityList = list.map {
                KitapTurEntity(
                    kitapTurId = it.id,
                    aciklama = it.aciklama,
                    resim = it.resim
                )
            }
            val insertEvent = nonFlowDbCall(ioDispatcher) {
                kitapTurRepository.saveKitapTurList(*kitapTurEntityList.toTypedArray())
            }
            if (insertEvent is BaseResourceEvent.Success)
                customSharedPreferences.putToSharedPref(PARAM_KITAPTUR_DB_KEY,true)
        }
    }
}