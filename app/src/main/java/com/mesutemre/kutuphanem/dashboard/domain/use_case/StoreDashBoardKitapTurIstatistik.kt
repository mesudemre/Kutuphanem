package com.mesutemre.kutuphanem.dashboard.domain.use_case

import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.dashboard.data.dao.entity.KitapTurIstatistikEntity
import com.mesutemre.kutuphanem.dashboard.data.remote.dto.KitapTurIstatistikDto
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import com.mesutemre.kutuphanem.util.DASHBOARD_KATEGORI_ISTATISTIK
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 25.09.2022
 */
class StoreDashBoardKitapTurIstatistik @Inject constructor(
    private val deleteKitapTurIstatistikUseCase: DeleteDashboardKitapTurIstatistikFromDbUseCase,
    private val saveKitapTurIstatistikUseCase: SaveDashboardKitapTurIstatistikIntoDbUseCase,
    private val customSharedPreferences: CustomSharedPreferences
){
    operator suspend fun invoke(list: List<KitapTurIstatistikDto>) {
        deleteKitapTurIstatistikUseCase().collectLatest {
            if (it is BaseResourceEvent.Success) {
                val kitapTurIstatistikEntityList = list.map {
                    KitapTurIstatistikEntity(
                        aciklama = it.aciklama,
                        adet = it.adet
                    )
                }
                saveKitapTurIstatistikUseCase(kitapTurIstatistikEntityList).collectLatest {
                    customSharedPreferences.putToSharedPref(DASHBOARD_KATEGORI_ISTATISTIK,true)
                }
            }
        }
    }
}