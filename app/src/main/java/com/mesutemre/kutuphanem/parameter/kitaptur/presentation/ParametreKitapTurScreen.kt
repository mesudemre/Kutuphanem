package com.mesutemre.kutuphanem.parameter.kitaptur.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.model.ERROR
import com.mesutemre.kutuphanem.model.QA_DLG
import com.mesutemre.kutuphanem.model.SUCCESS
import com.mesutemre.kutuphanem.parameter.components.ParametreDeleteSwipeBackground
import com.mesutemre.kutuphanem.parameter.components.ParametreRowItem
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem.util.customcomponents.progressbar.KutuphanemLoader
import com.mesutemre.kutuphanem.util.customcomponents.KutuphanemSearchInput
import com.mesutemre.kutuphanem.util.customcomponents.dialog.CustomKutuphanemDialog
import com.mesutemre.kutuphanem.util.customcomponents.error.KutuphanemErrorView
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ParametreKitapTurScreen(
    viewModel: ParametreKitapTurViewModel = hiltViewModel(),
    showSnackbar: (String, SnackbarDuration, Int) -> Unit
) {
    val state = viewModel.state.collectAsState().value

    when (state.kitapTurDelete) {
        is BaseResourceEvent.Success -> {
            LaunchedEffect(key1 = Unit) {
                showSnackbar(
                    state.kitapTurDelete.data?.statusMessage ?: "",
                    SnackbarDuration.Long,
                    SUCCESS
                )
            }
        }
        is BaseResourceEvent.Error -> {
            LaunchedEffect(key1 = Unit) {
                showSnackbar(
                    state.kitapTurDelete.message ?: "",
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
            title = stringResource(id = R.string.kitapTurSilmeTitle),
            description = (state.selectedKitapTur?.aciklama
                ?: "") + " " + stringResource(id = R.string.kitapTurSilmekIstiyormusunuz),
            onDismissDialog = {
                viewModel.onDismissParameterDeleteDialog()
            }) {
            viewModel.onClickDeleteKitapTur()
        }
    }

    if (state.kitapTurDelete is BaseResourceEvent.Loading) {
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
            .alpha(if (state.kitapTurDelete is BaseResourceEvent.Loading) 0.4f else 1f)
            .background(color = MaterialTheme.colorPalette.loginBackColor)
            .padding(start = 16.sdp, end = 16.sdp, top = 16.sdp)
    ) {
        KutuphanemSearchInput(
            text = state.kitapTurFilterText, modifier = Modifier
                .fillMaxWidth(),
            trailingIconId = if (state.kitapTurFilterText.isNotEmpty())  R.drawable.ic_circle_delete else null,
            trailingIconColor = MaterialTheme.colorPalette.secondaryGray,
            onTrailingIconClick = {
                                  
            },
            onValueChange = {
                viewModel.onSearchTextChangeValue(it)
            },
            placeholderText = stringResource(id = R.string.kitapTurLabel)
        )

        Box(
            modifier = Modifier
                .padding(top = 16.sdp)
                .fillMaxSize()
        ) {
            when (state.kitapTurList) {
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
                        errorText = if (state.kitapTurList.messageId != null)
                            stringResource(id = state.kitapTurList.messageId!!)
                        else
                            state.kitapTurList.message ?: ""
                    )
                }
                is BaseResourceEvent.Success -> {
                    SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing = state.swipeRefreshing),
                        onRefresh = {
                            viewModel.initKitapTurList(true)
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
                            items(state.kitapTurList.data!!) { kitapTur ->
                                val dismissState = rememberDismissState()
                                SwipeToDismiss(state = dismissState,
                                    modifier = Modifier.padding(vertical = 2.sdp),
                                    directions = setOf(
                                        DismissDirection.EndToStart
                                    ),
                                    dismissThresholds = { direction ->
                                        FractionalThreshold(0.2f)
                                    },
                                    background = {
                                        ParametreDeleteSwipeBackground(
                                            dismissState = dismissState,
                                            iconContentDescription = kitapTur.aciklama
                                        ) {
                                            viewModel.openDeleteConfirmDialog(kitapTur)
                                        }
                                    }) {
                                    ParametreRowItem(detail = kitapTur.aciklama)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}