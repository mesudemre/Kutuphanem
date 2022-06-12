package com.mesutemre.kutuphanem.parameter.yayinevi.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.model.ERROR
import com.mesutemre.kutuphanem.model.QA_DLG
import com.mesutemre.kutuphanem.model.SUCCESS
import com.mesutemre.kutuphanem.parameter.components.ParametreRowItem
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem.util.customcomponents.KutuphanemLoader
import com.mesutemre.kutuphanem.util.customcomponents.KutuphanemSearchInput
import com.mesutemre.kutuphanem.util.customcomponents.dialog.CustomKutuphanemDialog
import com.mesutemre.kutuphanem.util.customcomponents.error.KutuphanemErrorView

@Composable
fun ParametreYayinEviScreen(viewModel: ParametreYayinEviViewModel = hiltViewModel(),
                            showSnackbar: (String, SnackbarDuration, Int) -> Unit) {
    val state = viewModel.state.value

    when(state.yayinEviDelete) {
        is BaseResourceEvent.Success-> {
            LaunchedEffect(key1 = Unit) {
                showSnackbar(
                    state.yayinEviDelete.data?.statusMessage ?: "",
                    SnackbarDuration.Long,
                    SUCCESS
                )
            }
        }
        is BaseResourceEvent.Error-> {
            LaunchedEffect(key1 = Unit) {
                showSnackbar(
                    state.yayinEviDelete.message ?: "",
                    SnackbarDuration.Long,
                    ERROR
                )
            }
        }
    }

    if (state.isPopUpShow) {
        CustomKutuphanemDialog(
            modifier = Modifier
                .height(200.sdp)
                .width(400.sdp),
            type = QA_DLG,
            title = stringResource(id = R.string.yayilEviSilmeTitle),
            description = (state.selectedYayinevi?.aciklama
                ?: "") + " " + stringResource(id = R.string.yayinEviSilmekIstiyormusunuz),
            onDismissDialog = {
                viewModel.onDismissParameterDeleteDialog()
            }) {
            viewModel.onClickDeleteYayinevi()
        }
    }

    if (state.yayinEviDelete is BaseResourceEvent.Loading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            KutuphanemLoader(
                modifier = Modifier
                    .width(220.sdp)
                    .height(220.sdp)
                    .align(Alignment.Center)
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .alpha(if (state.yayinEviDelete is BaseResourceEvent.Loading) 0.4f else 1f)
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
                    KutuphanemLoader(
                        modifier = Modifier
                            .width(220.sdp)
                            .height(220.sdp)
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
                            items(state.yayinEviList.data!!) { yayinEvi ->
                                ParametreRowItem(detail = yayinEvi.aciklama) {
                                    viewModel.openDeleteConfirmDialog(yayinEvi)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}