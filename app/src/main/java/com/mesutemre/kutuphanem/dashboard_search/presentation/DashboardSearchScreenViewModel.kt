package com.mesutemre.kutuphanem.dashboard_search.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.mesutemre.kutuphanem.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 30.08.2022
 */
@HiltViewModel
class DashboardSearchScreenViewModel @Inject constructor(): BaseViewModel(){

    private val _dashboardSearchState = mutableStateOf(DashboardSearchScreenState())
    val dashboardSearchState: State<DashboardSearchScreenState> = _dashboardSearchState

    fun onSearchKitapYazar(searchText:String) {
        _dashboardSearchState.value = _dashboardSearchState.value.copy(
            searchText = searchText
        )
    }

    fun onSearchBack(){
        _dashboardSearchState.value = _dashboardSearchState.value.copy(
            searchText = "",
            searchScreenVisibility = false
        )
    }
}