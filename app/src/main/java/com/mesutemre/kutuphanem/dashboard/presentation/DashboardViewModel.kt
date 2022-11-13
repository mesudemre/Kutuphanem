package com.mesutemre.kutuphanem.dashboard.presentation

import androidx.lifecycle.viewModelScope
import com.mesutemre.kutuphanem.base.BaseViewModel
import com.mesutemre.kutuphanem.dashboard.domain.use_case.*
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
    private val getKitapYilIstatistik: GetKitapYilIstatistik
) : BaseViewModel() {

    private val _dashboardState = MutableStateFlow(DashboardState())
    val dashboardState: StateFlow<DashboardState> = _dashboardState

    init {
        _dashboardState.update {
            it.copy(
                introductionList = getDashboardIntroductionList()
            )
        }
        viewModelScope.launch {
            async { fillDashBoardUserInfo() }
            async {
                fillKategoriList()
                fillKitapTurIstatistik()
            }
            async { fillKitapYilIstatistik() }
        }
    }

    private suspend fun fillDashBoardUserInfo() {
        getDashBoardKullaniciBilgi().collectLatest { response ->
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
            _dashboardState.update {
                it.copy(
                    kitapTurIstatistikResource = response
                )
            }
        }
    }

    private suspend fun fillKitapYilIstatistik() {
        getKitapYilIstatistik().collectLatest { response ->
            _dashboardState.update {
                it.copy(
                    kitapYilIstatistikResource = response
                )
            }
        }
    }
}