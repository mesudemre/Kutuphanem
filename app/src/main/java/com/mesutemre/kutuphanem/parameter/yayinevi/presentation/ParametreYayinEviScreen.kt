package com.mesutemre.kutuphanem.parameter.yayinevi.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.parameter.components.ParametreRowItem
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem.util.customcomponents.KutuphanemProgressIndicator
import com.mesutemre.kutuphanem.util.customcomponents.KutuphanemSearchInput
import com.mesutemre.kutuphanem.util.customcomponents.error.KutuphanemErrorView

@Composable
fun ParametreYayinEviScreen(viewModel: ParametreYayinEviViewModel = hiltViewModel()) {
    val state = viewModel.state.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorPalette.loginBackColor)
            .padding(start = 16.sdp, end = 16.sdp, top = 16.sdp)
    ) {
        KutuphanemSearchInput(
            text = state.yayinEviFilterText, modifier = Modifier
                .fillMaxWidth(), onValueChange = {
                viewModel.onSearchTextChangeValue(it)
            },
            placeholderText = stringResource(id = R.string.yayinEviLabel)
        )

        Box(
            modifier = Modifier
                .padding(top = 16.sdp)
                .fillMaxSize()
        ) {
            when (state.yayinEviList) {
                is BaseResourceEvent.Loading -> {
                    KutuphanemProgressIndicator(
                        modifier = Modifier
                            .width(40.sdp)
                            .height(40.sdp)
                            .align(Alignment.Center)
                    )
                }
                is BaseResourceEvent.Error -> {
                    KutuphanemErrorView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.sdp),
                        errorText = if (state.yayinEviList.messageId != null)
                            stringResource(id = state.yayinEviList.messageId)
                    else
                            state.yayinEviList.message ?: ""
                    )
                }
                is BaseResourceEvent.Success -> {
                    SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing = state.swipeRefreshing),
                        onRefresh = {
                            viewModel.initYayinEviList(true)
                        },
                        indicator = { state, trigger ->
                            SwipeRefreshIndicator(
                                state = state,
                                refreshTriggerDistance = trigger,
                                scale = true,
                                backgroundColor = MaterialTheme.colorPalette.white,
                                contentColor = MaterialTheme.colorPalette.lacivert,
                                shape = RoundedCornerShape(50.sdp),
                            )
                        }
                    ) {
                        LazyColumn(contentPadding = PaddingValues(4.sdp)) {
                            items(state.yayinEviList.data!!) {yayinEvi->
                                ParametreRowItem(detail = yayinEvi.aciklama)
                            }
                        }
                    }
                }
            }
        }
    }
}