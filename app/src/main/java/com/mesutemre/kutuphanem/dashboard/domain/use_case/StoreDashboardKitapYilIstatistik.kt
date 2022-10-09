package com.mesutemre.kutuphanem.dashboard.domain.use_case

import com.mesutemre.kutuphanem.dashboard.data.dao.entity.KitapYilIstatistikEntity
import com.mesutemre.kutuphanem.dashboard.data.remote.dto.KitapYilIstatistikDto
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import com.mesutemre.kutuphanem.util.DASHBOARD_YIL_ISTATISTIK
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 2.10.2022
 */
class StoreDashboardKitapYilIstatistik @Inject constructor(
    private val customSharedPreferences: CustomSharedPreferences,
    private val saveDashboardKitapYilIstatistikIntoDbUseCase: SaveDashboardKitapYilIstatistikIntoDbUseCase,
    private val deleteDashboardKitapYilIstatistikFromDbUseCase: DeleteDashboardKitapYilIstatistikFromDbUseCase
) {
    operator suspend fun invoke(list: List<KitapYilIstatistikDto>) {
        deleteDashboardKitapYilIstatistikFromDbUseCase().collectLatest {
            val kitapYilIstatistikEntityList = list.map {
                KitapYilIstatistikEntity(
                    yil = it.yil,
                    adet = it.adet
                )
            }
            saveDashboardKitapYilIstatistikIntoDbUseCase(kitapYilIstatistikEntityList).collectLatest {
                customSharedPreferences.putToSharedPref(DASHBOARD_YIL_ISTATISTIK,true)
            }
        }
    }
}