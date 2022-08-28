package com.mesutemre.kutuphanem.dashboard.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.mesutemre.kutuphanem.base.BaseViewModel
import com.mesutemre.kutuphanem.dashboard.domain.use_case.GetDasboardKullaniciBilgi
import com.mesutemre.kutuphanem.dashboard.domain.use_case.GetDashboardIntroductionList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 28.08.2022
 */
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getDashboardIntroductionList: GetDashboardIntroductionList,
    private val getDashBoardKullaniciBilgi: GetDasboardKullaniciBilgi
) :BaseViewModel(){
    
    private val _dashboardState = mutableStateOf(DashboardState())
    val dashboardState: State<DashboardState> = _dashboardState
    
    init {
        _dashboardState.value = _dashboardState.value.copy(
            introductionList = getDashboardIntroductionList()
        )
        viewModelScope.launch {
            async { fillDashBoardUserInfo() }
        }
    }

    private suspend fun fillDashBoardUserInfo() {
        getDashBoardKullaniciBilgi().collectLatest {
            _dashboardState.value = _dashboardState.value.copy(
                kullaniciBilgi = it
            )
        }
    }
}