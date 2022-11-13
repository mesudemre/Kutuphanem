package com.mesutemre.kutuphanem.parameter.kitaptur.domain.use_case

import com.mesutemre.kutuphanem.parameter.kitaptur.data.dao.entity.KitapTurEntity
import com.mesutemre.kutuphanem.parameter.kitaptur.data.remote.dto.KitapTurDto
import com.mesutemre.kutuphanem.parameter.kitaptur.data.repository.KitapTurRepository
import com.mesutemre.kutuphanem.util.PARAM_KITAPTUR_DB_KEY
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 3.07.2022
 */
class StoreKitapTurParametre @Inject constructor(
    private val kitapTurRepository: KitapTurRepository,
    private val saveKitapTurIntoDbUseCase: SaveKitapTurIntoDbUseCase,
    private val deleteKitapTurFromDbUseCase: DeleteKitapTurFromDbUseCase
) {
    operator suspend fun invoke(list: List<KitapTurDto>) {
        deleteKitapTurFromDbUseCase().collectLatest {
            if (it is BaseResourceEvent.Success) {
                val kitapTurEntityList = list.map {
                    KitapTurEntity(
                        kitapTurId = it.id,
                        aciklama = it.aciklama,
                        resim = it.resim
                    )
                }
                saveKitapTurIntoDbUseCase(kitapTurEntityList).collectLatest {
                    kitapTurRepository.saveKitapTurDbKayitToDataStore(PARAM_KITAPTUR_DB_KEY, true)
                }
            }
        }
    }
}