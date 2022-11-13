package com.mesutemre.kutuphanem.parameter.yayinevi.domain.use_case

import com.mesutemre.kutuphanem.parameter.yayinevi.data.dao.entity.YayinEviEntity
import com.mesutemre.kutuphanem.parameter.yayinevi.domain.model.YayinEviItem
import com.mesutemre.kutuphanem.parameter.yayinevi.domain.repository.YayinEviRepository
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
    private val yayinEviRepository: YayinEviRepository
) : IDbCall by DbCallUseCase() {

    suspend operator fun invoke(list: List<YayinEviItem>) {
        deleteYayinEviFromDbUseCase().collectLatest {
            saveYayinEviIntoDbUseCase(*list.map {
                YayinEviEntity(
                    yayinEviId = it.id,
                    aciklama = it.aciklama
                )
            }).collectLatest {
                yayinEviRepository.saveYayinEviDbKayitToDataStore(PARAM_YAYINEVI_DB_KEY, true)
            }
        }
    }
}