package com.mesutemre.kutuphanem.dashboard_search.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.mesutemre.kutuphanem.base.BaseViewModel
import com.mesutemre.kutuphanem.dashboard_search.domain.use_case.GetKitapSearchListUseCase
import com.mesutemre.kutuphanem.dashboard_search.domain.use_case.GetSearchHistoryListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 30.08.2022
 */
@HiltViewModel
class DashboardSearchScreenViewModel @Inject constructor(
    private val getSearchHistoryList: GetSearchHistoryListUseCase,
    private val getKitapSearchListUseCase: GetKitapSearchListUseCase
): BaseViewModel(){

    private val _dashboardSearchState = mutableStateOf(DashboardSearchScreenState())
    val dashboardSearchState: State<DashboardSearchScreenState> = _dashboardSearchState

    init {
        viewModelScope.launch {
            getSearchHistoryList().collectLatest {
                _dashboardSearchState.value = _dashboardSearchState.value.copy(
                    historyListResource = it
                )
            }
        }
    }

    fun onSearchKitapYazar(searchText:String) {
        _dashboardSearchState.value = _dashboardSearchState.value.copy(
            searchText = searchText,
            isSearching = searchText.length>2
        )
        if (_dashboardSearchState.value.isSearching) {
            searchKitapYazar(searchText)
        }
    }

    fun onSearchBack(){
        _dashboardSearchState.value = _dashboardSearchState.value.copy(
            searchText = "",
            isSearching = false,
            searchScreenVisibility = false
        )
    }

    private fun searchKitapYazar(searchText: String) {
        viewModelScope.launch {
            getKitapSearchListUseCase(searchText).collectLatest {
                _dashboardSearchState.value = _dashboardSearchState.value.copy(
                    searchResultResource = it
                )
            }
        }
    }
}