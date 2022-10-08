package com.mesutemre.kutuphanem.dashboard_search.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.dashboard_search.data.dao.entity.DashBoardSearchHistoryEntity
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem.util.customcomponents.KutuphanemLoader

@Composable
fun DashboardSearchHistory(historyListResource: BaseResourceEvent<List<DashBoardSearchHistoryEntity>>) {
    when (historyListResource) {
        is BaseResourceEvent.Loading -> {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                KutuphanemLoader(modifier = Modifier.padding(top = 16.sdp))
            }
        }
        is BaseResourceEvent.Success -> {
            if (historyListResource.data.isNullOrEmpty()) {
                NoSearchDoneYet()
            }else {
                LazyColumn{
                    items(historyListResource.data!!) {history->
                        DashBoardSearchHistoryRowItem(
                            kitapAd = history.kitapAd ?: "",
                            yazarAd = history.yazarAd ?: ""
                        ) {

                        }
                    }
                }
            }
        }
        is BaseResourceEvent.Error -> {
            NoSearchDoneYet()
        }
    }
}