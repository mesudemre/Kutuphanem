package com.mesutemre.kutuphanem.parameter.yayinevi.domain.use_case

import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.base.BaseUseCase
import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem.parameter.yayinevi.data.dao.entity.YayinEviEntity
import com.mesutemre.kutuphanem.parameter.yayinevi.data.remote.dto.YayinEviDto
import com.mesutemre.kutuphanem.parameter.yayinevi.domain.repository.YayinEviRepository
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import com.mesutemre.kutuphanem.util.PARAM_YAYINEVI_DB_KEY
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 21.05.2022
 */
class StoreYayinEviParametre @Inject constructor(
    private val yayinEviRepository: YayinEviRepository,
    private val customSharedPreferences: CustomSharedPreferences,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): BaseUseCase(){

    suspend operator fun invoke(list: List<YayinEviDto>) {
        val deleteEvent = nonFlowDbCall(ioDispatcher) {
            yayinEviRepository.deleteYayinEviList()
        }
        if (deleteEvent is BaseResourceEvent.Success) {
            val yayinEviEntityList = list.map {
                YayinEviEntity(
                    yayinEviId = it.id,
                    aciklama = it.aciklama
                )
            }
            val insertEvent = nonFlowDbCall(ioDispatcher) {
                yayinEviRepository.saveYayinEviList(*yayinEviEntityList.toTypedArray())
            }
            if (insertEvent is BaseResourceEvent.Success)
                customSharedPreferences.putToSharedPref(PARAM_YAYINEVI_DB_KEY,true)
        }
    }
}