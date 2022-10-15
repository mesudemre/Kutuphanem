package com.mesutemre.kutuphanem.dashboard_search.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.mesutemre.kutuphanem.base.BaseViewModel
import com.mesutemre.kutuphanem.dashboard_search.domain.use_case.GetKitapSearchListUseCase
import com.mesutemre.kutuphanem.dashboard_search.domain.use_case.GetSearchHistoryListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
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

    private val _dashboardSearchState = MutableStateFlow(DashboardSearchScreenState())
    val dashboardSearchState: StateFlow<DashboardSearchScreenState> = _dashboardSearchState

    init {
        viewModelScope.launch {
            getSearchHistoryList().collectLatest {result->
                _dashboardSearchState.update {
                    it.copy(
                        historyListResource = result
                    )
                }
            }
        }
    }

    fun onSearchKitapYazar(searchText:String) {
        _dashboardSearchState.update {
            it.copy(
                searchText = searchText,
                isSearching = searchText.length>2
            )
        }
        if (_dashboardSearchState.value.isSearching) {
            searchKitapYazar(searchText)
        }
    }

    fun onSearchBack(){
        _dashboardSearchState.update {
            it.copy(
                searchText = "",
                isSearching = false,
                searchScreenVisibility = false
            )
        }
    }

    private fun searchKitapYazar(searchText: String) {
        viewModelScope.launch {
            getKitapSearchListUseCase(searchText).collectLatest {response->
                _dashboardSearchState.update {
                    it.copy(
                        searchResultResource = response
                    )
                }
            }
        }
    }

    fun onClearSearch() {
        _dashboardSearchState.update {
            it.copy(
                searchText = "",
                isSearching = false
            )
        }
    }
}