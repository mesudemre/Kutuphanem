package com.mesutemre.kutuphanem.dashboard.presentation

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import com.mesutemre.kutuphanem.base.BaseViewModel
import com.mesutemre.kutuphanem.dashboard.domain.use_case.*
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import com.mesutemre.kutuphanem_ui.chart.KutuphanemBarChartInput
import com.mesutemre.kutuphanem_ui.chart.KutuphanemPieChartInput
import com.mesutemre.kutuphanem_ui.theme.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 28.08.2022
 */
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getDashboardIntroductionList: GetDashboardIntroductionList,
    private val getDashBoardKullaniciBilgi: GetDasboardKullaniciBilgi,
    private val getDashboardKategoriList: GetDashboardKategoriList,
    private val getKitapTurIstatistik: GetKitapTurIstatistik,
    private val getKitapYilIstatistik: GetKitapYilIstatistik,
    private val saveDashboardUserInfoDataStore: SaveDashboardUserInfoDataStore
) : BaseViewModel() {

    private val _dashboardState = MutableStateFlow(DashboardState())
    val dashboardState: StateFlow<DashboardState> = _dashboardState

    private val colorList = listOf<Color>(
        etonBlue,
        morningBlue,
        salmonPink,
        acikMor,
        aero
    )

    init {
        _dashboardState.update {
            it.copy(
                introductionList = getDashboardIntroductionList()
            )
        }
        viewModelScope.launch {
            async { fillDashBoardUserInfo() }
            async { fillKategoriList() }
            async { fillKitapTurIstatistik() }
            async { fillKitapYilIstatistik() }
        }
    }

    private suspend fun fillDashBoardUserInfo() {
        getDashBoardKullaniciBilgi().collectLatest { response ->
            if (response is BaseResourceEvent.Success) {
                response.data?.let {
                    saveDashboardUserInfoDataStore(it)
                }
            }
            _dashboardState.update {
                it.copy(
                    kullaniciBilgi = response
                )
            }
        }
    }

    private suspend fun fillKategoriList() {
        getDashboardKategoriList().collectLatest { response ->
            _dashboardState.update {
                it.copy(
                    kategoriListeResource = response
                )
            }
        }
    }

    private suspend fun fillKitapTurIstatistik() {
        getKitapTurIstatistik().collectLatest { response ->
            var kitapTurIstatistikList = listOf<KutuphanemPieChartInput>()
            if (response is BaseResourceEvent.Success) {
                kitapTurIstatistikList = response.data?.sortedByDescending {
                    it.adet
                }?.take(5)?.mapIndexed { index, dashboardKitapTurIstatistikItem ->
                    KutuphanemPieChartInput(
                        value = dashboardKitapTurIstatistikItem.adet.toInt(),
                        description = dashboardKitapTurIstatistikItem.aciklama,
                        color = colorList[index]
                    )
                } ?: emptyList()
            }
            _dashboardState.update {
                it.copy(
                    kitapTurIstatistikResource = response,
                    kitapTurIstatistikList = kitapTurIstatistikList
                )
            }
        }
    }

    private suspend fun fillKitapYilIstatistik() {
        var kitapYilIstatistikList = listOf<KutuphanemBarChartInput>()
        getKitapYilIstatistik().collectLatest { response ->
            if (response is BaseResourceEvent.Success) {
                kitapYilIstatistikList = response.data?.sortedByDescending {
                    it.adet
                }?.take(5)?.mapIndexed { index, dashboardKitapYilIstatistikItem ->
                    KutuphanemBarChartInput(
                        value = dashboardKitapYilIstatistikItem.adet.toInt(),
                        description = dashboardKitapYilIstatistikItem.aciklama,
                        color = colorList[index]
                    )
                } ?: emptyList()
            }
            _dashboardState.update {
                it.copy(
                    kitapYilIstatistikResource = response,
                    kitapYilIstatistikList = kitapYilIstatistikList
                )
            }
        }
    }
}