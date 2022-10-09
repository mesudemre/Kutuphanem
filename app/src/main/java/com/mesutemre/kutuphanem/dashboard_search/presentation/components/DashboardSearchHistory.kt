package com.mesutemre.kutuphanem.dashboard_search.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.dashboard_search.data.dao.entity.DashBoardSearchHistoryEntity
import com.mesutemre.kutuphanem.ui.theme.colorPalette
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
                NoSearchDoneYet(stringResource(id = R.string.search_dashboard_not_search_yet))
            }else {
                LazyColumn(modifier = Modifier.padding(top = 8.sdp)){
                    items(historyListResource.data!!) {history->
                        DashBoardSearchHistoryRowItem(
                            kitapAd = history.kitapAd ?: "",
                            yazarAd = history.yazarAd ?: ""
                        ) {

                        }
                        Divider(
                            modifier = Modifier.padding(vertical = 4.sdp,horizontal = 16.sdp),
                            thickness = 1.sdp,
                            color = MaterialTheme.colorPalette.lacivert
                        )
                    }
                }
            }
        }
        is BaseResourceEvent.Error -> {
            NoSearchDoneYet(stringResource(id = R.string.search_dashboard_not_search_yet))
        }
    }
}