package com.mesutemre.kutuphanem.kitap_detay.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mesutemre.kutuphanem.kitap_detay.domain.model.KitapDetayBottomSheetState
import com.mesutemre.kutuphanem.kitap_detay.presentation.components.body.KitapDetayInfoBodyArea
import com.mesutemre.kutuphanem.kitap_detay.presentation.components.bottomsheet.KitapDetayAciklamaBottomSheet
import com.mesutemre.kutuphanem.kitap_detay.presentation.components.header.KitapDetayHeaderArea
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.sdp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun KitapDetayScreen(
    showSnackbar: (String, SnackbarDuration, Int) -> Unit,
    navController: NavController,
    viewModel: KitapDetayScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState =
        BottomSheetState(BottomSheetValue.Collapsed)
    )
    val coroutineScope = rememberCoroutineScope()
    BackHandler {
        if (bottomSheetScaffoldState.bottomSheetState.isExpanded) {
            coroutineScope.launch {
                bottomSheetScaffoldState.bottomSheetState.animateTo(
                    BottomSheetValue.Collapsed,
                    tween(500)
                )
            }
        } else {
            navController.popBackStack()
        }
    }

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            if (state.value.kitapDetayBottomSheetState == KitapDetayBottomSheetState.KITAP_DETAY_ACIKLAMA) {
                KitapDetayAciklamaBottomSheet(
                    state.value.kitapDetayAciklama ?: ""
                )
            } else if (state.value.kitapDetayBottomSheetState == KitapDetayBottomSheetState.YORUM) {

            }
        },
        sheetBackgroundColor = MaterialTheme.colorPalette.white,
        sheetContentColor = MaterialTheme.colorPalette.loginBackColor,
        sheetPeekHeight = (-50).sdp
    ) {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorPalette.loginBackColor)
                .verticalScroll(scrollState)
        ) {
            KitapDetayHeaderArea(
                kitapDetayItemResource = state.value.kitapDetayItemResource
            )
            Spacer(modifier = Modifier.height(12.sdp))
            KitapDetayInfoBodyArea(
                kitapDetayItemResource = state.value.kitapDetayItemResource
            ) { aciklama ->
                coroutineScope.launch {
                    viewModel.onExpandKitapDetayBottomSheet(aciklama)
                    bottomSheetScaffoldState.bottomSheetState.animateTo(
                        BottomSheetValue.Expanded,
                        tween(500)
                    )
                }
            }

        }
    }
}