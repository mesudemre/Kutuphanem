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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mesutemre.kutuphanem.kitap_detay.domain.model.KitapDetayBottomSheetState
import com.mesutemre.kutuphanem.kitap_detay.presentation.components.body.KitapDetayInfoBodyArea
import com.mesutemre.kutuphanem.kitap_detay.presentation.components.bottomsheet.KitapDetayAciklamaBottomSheet
import com.mesutemre.kutuphanem.kitap_detay.presentation.components.bottomsheet.KitapYorumListeBottomSheet
import com.mesutemre.kutuphanem.kitap_detay.presentation.components.header.KitapDetayHeaderArea
import com.mesutemre.kutuphanem.model.ERROR
import com.mesutemre.kutuphanem.model.SUCCESS
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.util.customcomponents.snackbar.KutuphanemSnackBarHost
import com.mesutemre.kutuphanem.util.rememberKutuphanemAppState
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import com.mesutemre.kutuphanem_ui.theme.sdp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun KitapDetayScreen(
    navController: NavController,
    viewModel: KitapDetayScreenViewModel = hiltViewModel()
) {
    val kutuphanemAppState = rememberKutuphanemAppState()
    val state = viewModel.state.collectAsState()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState =
        BottomSheetState(BottomSheetValue.Collapsed)
    )
    val coroutineScope = rememberCoroutineScope()
    val localFocusManager = LocalFocusManager.current
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

    val defaultYorumModelList by remember {
        derivedStateOf {
            mutableStateOf(
                viewModel.getDefaultBottomsheetYorumListe()
            )
        }
    }
    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        snackbarHost = {
            KutuphanemSnackBarHost(state = kutuphanemAppState.kutuphanemSnackbarState)
        },
        sheetContent = {
            if (state.value.kitapDetayBottomSheetState == KitapDetayBottomSheetState.KITAP_DETAY_ACIKLAMA) {
                KitapDetayAciklamaBottomSheet(
                    state.value.kitapDetayAciklama ?: ""
                )
            } else if (state.value.kitapDetayBottomSheetState == KitapDetayBottomSheetState.YORUM) {
                KitapYorumListeBottomSheet(
                    yorum = state.value.yorumText,
                    kullaniciResim = state.value.userInfo?.resim ?: "",
                    kitapYorumKaydetResource = state.value.kitapYorumKaydetResource,
                    kitapYorumListeResource = state.value.kitapYorumListeResouce,
                    yorumListeModel = state.value.yorumListeModel ?: defaultYorumModelList.value,
                    getKitapYorumListe = {
                        viewModel.getKitapYorumListe(true)
                    },
                    kitapYorumKaydet = {
                        localFocusManager.clearFocus()
                        viewModel.kitapYorumKaydet(it)
                    },
                    onYorumChange = {
                        viewModel.onChangeYorumText(it)
                    },
                    onCloseBottomSheet = {
                        coroutineScope.launch {
                            bottomSheetScaffoldState.bottomSheetState.animateTo(
                                BottomSheetValue.Collapsed,
                                tween(500)
                            )
                        }
                    }
                )
            }
        },
        sheetBackgroundColor = MaterialTheme.colorPalette.white,
        sheetContentColor = MaterialTheme.colorPalette.loginBackColor,
        sheetPeekHeight = (-50).sdp
    ) {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorPalette.loginBackColor)
                .verticalScroll(scrollState)
        ) {
            when (state.value.kitapYorumKaydetResource) {
                is BaseResourceEvent.Success -> {
                    LaunchedEffect(key1 = Unit) {
                        kutuphanemAppState.showSnackbar(
                            state.value.kitapYorumKaydetResource.data?.statusMessage!!,
                            SnackbarDuration.Short,
                            SUCCESS
                        )
                    }
                }
                is BaseResourceEvent.Error -> {
                    LaunchedEffect(key1 = Unit) {
                        kutuphanemAppState.showSnackbar(
                            state.value.kitapYorumKaydetResource.data?.statusMessage!!,
                            SnackbarDuration.Short,
                            ERROR
                        )
                    }
                }
                else -> {}
            }

            KitapDetayHeaderArea(
                kitapDetayItemResource = state.value.kitapDetayItemResource
            )

            Spacer(modifier = Modifier.height(12.sdp))
            KitapDetayInfoBodyArea(
                kitapDetayItemResource = state.value.kitapDetayItemResource,
                changeBottomSheetState = {
                    coroutineScope.launch {
                        viewModel.onExpandKitapDetayBottomSheet(it)
                        bottomSheetScaffoldState.bottomSheetState.animateTo(
                            BottomSheetValue.Expanded,
                            tween(500)
                        )
                    }
                },
                onClickYorumArea = {
                    coroutineScope.launch {
                        viewModel.onExpandYorumBottomSheet()
                        bottomSheetScaffoldState.bottomSheetState.animateTo(
                            BottomSheetValue.Expanded,
                            tween(500)
                        )
                    }.invokeOnCompletion {
                        viewModel.getKitapYorumListe(false)
                    }
                }
            )
        }

    }
}