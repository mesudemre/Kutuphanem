package com.mesutemre.kutuphanem.dashboard_search.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.dashboard_search.data.dao.entity.DashBoardSearchHistoryEntity
import com.mesutemre.kutuphanem.ui.theme.*
import com.mesutemre.kutuphanem.util.customcomponents.progressbar.KutuphanemLoader
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent

@Composable
fun DashboardSearchHistory(
    historyListResource: BaseResourceEvent<List<DashBoardSearchHistoryEntity>>,
    onClearSearchHistory: () -> Unit
) {
    when (historyListResource) {
        is BaseResourceEvent.Loading -> {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                KutuphanemLoader(modifier = Modifier.padding(top = 16.sdp))
            }
        }
        is BaseResourceEvent.Success -> {
            if (historyListResource.data.isNullOrEmpty()) {
                NoSearchDoneYet(stringResource(id = R.string.search_dashboard_not_search_yet))
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.sdp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.sdp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier
                                .weight(1f),
                            text = stringResource(id = R.string.search_dashboard_searchHistoryTitle),
                            style = MaterialTheme.typography.smallUbuntuTransparentBold
                        )
                        Text(
                            modifier = Modifier
                                .clickable {
                                    onClearSearchHistory()
                                },
                            text = stringResource(id = R.string.search_dashboard_searchHistoryClear),
                            style = MaterialTheme.typography.smallUbuntuPrimaryBold
                        )
                    }
                    Divider(
                        modifier = Modifier.padding(vertical = 8.sdp),
                        thickness = 1.sdp,
                        color = MaterialTheme.colorPalette.otherGrayLight
                    )
                    LazyColumn(modifier = Modifier.padding(top = 8.sdp)) {
                        items(historyListResource.data!!) { history ->
                            DashBoardSearchHistoryRowItem(
                                kitapAd = history.kitapAd ?: "",
                                yazarAd = history.yazarAd ?: "",
                                isHistory = true
                            ) {
                            }
                            Divider(
                                modifier = Modifier.padding(vertical = 4.sdp, horizontal = 16.sdp),
                                thickness = 1.sdp,
                                color = MaterialTheme.colorPalette.otherGrayLight
                            )
                        }
                    }
                }
            }
        }
        is BaseResourceEvent.Error -> {
            NoSearchDoneYet(stringResource(id = R.string.search_dashboard_not_search_yet))
        }
        else -> {}
    }
}