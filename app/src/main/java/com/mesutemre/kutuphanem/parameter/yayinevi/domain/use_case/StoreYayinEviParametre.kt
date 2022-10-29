package com.mesutemre.kutuphanem.parameter.yayinevi.domain.use_case

import com.mesutemre.kutuphanem.parameter.yayinevi.data.dao.entity.YayinEviEntity
import com.mesutemre.kutuphanem.parameter.yayinevi.domain.model.YayinEviItem
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import com.mesutemre.kutuphanem.util.PARAM_YAYINEVI_DB_KEY
import com.mesutemre.kutuphanem_base.use_case.DbCallUseCase
import com.mesutemre.kutuphanem_base.use_case.IDbCall
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 21.05.2022
 */
class StoreYayinEviParametre @Inject constructor(
    private val deleteYayinEviFromDbUseCase: DeleteYayinEviFromDbUseCase,
    private val saveYayinEviIntoDbUseCase: SaveYayinEviIntoDbUseCase,
    private val customSharedPreferences: CustomSharedPreferences
) : IDbCall by DbCallUseCase() {

    operator suspend fun invoke(list: List<YayinEviItem>) {
        deleteYayinEviFromDbUseCase().collectLatest {
            saveYayinEviIntoDbUseCase(*list.map {
                YayinEviEntity(
                    yayinEviId = it.id,
                    aciklama = it.aciklama
                )
            }).collectLatest {
                customSharedPreferences.putToSharedPref(PARAM_YAYINEVI_DB_KEY, true)
            }
        }
    }
}