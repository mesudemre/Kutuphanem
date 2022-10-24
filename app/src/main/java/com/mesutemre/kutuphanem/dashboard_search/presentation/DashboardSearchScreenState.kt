package com.mesutemre.kutuphanem.dashboard_search.presentation

import com.mesutemre.kutuphanem.dashboard_search.data.dao.entity.DashBoardSearchHistoryEntity
import com.mesutemre.kutuphanem.dashboard_search.domain.model.KitapSearchItem
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent

data class DashboardSearchScreenState(
    val searchText:String = "",
    val isSearching:Boolean = false,
    val historyListResource: BaseResourceEvent<List<DashBoardSearchHistoryEntity>> = BaseResourceEvent.Nothing(),
    val searchScreenVisibility: Boolean = true,
    val searchResultResource: BaseResourceEvent<List<KitapSearchItem>> = BaseResourceEvent.Nothing(),
    val searchResultSaveResource: BaseResourceEvent<Unit> = BaseResourceEvent.Nothing()
)
