package com.mesutemre.kutuphanem.parameter.yayinevi.domain.use_case

import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.parameter.yayinevi.data.dao.entity.YayinEviEntity
import com.mesutemre.kutuphanem.parameter.yayinevi.data.remote.dto.YayinEviDto
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import com.mesutemre.kutuphanem.util.PARAM_YAYINEVI_DB_KEY
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 21.05.2022
 */
class StoreYayinEviParametre @Inject constructor(
    private val deleteYayinEviFromDbUseCase: DeleteYayinEviFromDbUseCase,
    private val saveYayinEviIntoDbUseCase: SaveYayinEviIntoDbUseCase,
    private val customSharedPreferences: CustomSharedPreferences
){

    operator fun invoke(list: List<YayinEviDto>) {
        val deleteEvent = deleteYayinEviFromDbUseCase()
        if (deleteEvent is BaseResourceEvent.Success<*>) {
            val yayinEviEntityList = list.map {
                YayinEviEntity(
                    yayinEviId = it.id,
                    aciklama = it.aciklama
                )
            }
            val insertEvent = saveYayinEviIntoDbUseCase(yayinEviEntityList)
            if (insertEvent is BaseResourceEvent.Success<*>)
                customSharedPreferences.putToSharedPref(PARAM_YAYINEVI_DB_KEY,true)
        }
    }
}