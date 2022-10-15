package com.mesutemre.kutuphanem.dashboard_search.presentation

import androidx.lifecycle.viewModelScope
import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.base.BaseViewModel
import com.mesutemre.kutuphanem.dashboard_search.domain.model.KitapSearchItem
import com.mesutemre.kutuphanem.dashboard_search.domain.use_case.ClearSearchHistoryUseCase
import com.mesutemre.kutuphanem.dashboard_search.domain.use_case.GetKitapSearchListUseCase
import com.mesutemre.kutuphanem.dashboard_search.domain.use_case.GetSearchHistoryListUseCase
import com.mesutemre.kutuphanem.dashboard_search.domain.use_case.SaveSearchHistoryItemToDbUseCase
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
    private val getKitapSearchListUseCase: GetKitapSearchListUseCase,
    private val saveSearchHistoryItemToDbUseCase: SaveSearchHistoryItemToDbUseCase,
    private val clearSearchHistoryUseCase: ClearSearchHistoryUseCase
) : BaseViewModel() {

    private val _dashboardSearchState = MutableStateFlow(DashboardSearchScreenState())
    val dashboardSearchState: StateFlow<DashboardSearchScreenState> = _dashboardSearchState

    init {
        viewModelScope.launch {
            getHistoryList()
        }

    }

     private suspend fun getHistoryList() {
        getSearchHistoryList().collectLatest { result ->
            _dashboardSearchState.update {
                it.copy(
                    historyListResource = result
                )
            }
        }
    }

    fun onSearchKitapYazar(searchText: String) {
        _dashboardSearchState.update {
            it.copy(
                searchText = searchText,
                isSearching = searchText.length > 2
            )
        }
        if (_dashboardSearchState.value.isSearching) {
            searchKitapYazar(searchText)
        }
    }

    fun onSearchBack() {
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
            getKitapSearchListUseCase(searchText).collectLatest { response ->
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
                isSearching = false,
                searchResultResource = BaseResourceEvent.Nothing()
            )
        }
    }

    fun saveSearchHistory(item: KitapSearchItem) {
        viewModelScope.launch {
            saveSearchHistoryItemToDbUseCase(item).collectLatest { response ->
                //TODO : Burada navigation sağlanacak...
            }
        }
    }

    fun clearSearchHistory() {
        viewModelScope.launch {
            clearSearchHistoryUseCase().collectLatest { result->
                if (result is BaseResourceEvent.Success)
                    getHistoryList()
            }
        }
    }
}