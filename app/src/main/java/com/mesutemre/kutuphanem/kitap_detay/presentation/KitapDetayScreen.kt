package com.mesutemre.kutuphanem.kitap_detay.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.kitap_detay.domain.model.KitapDetayBottomSheetState
import com.mesutemre.kutuphanem.kitap_detay.presentation.components.body.KitapDetayRatingArea
import com.mesutemre.kutuphanem.kitap_detay.presentation.components.body.KitapIslemButtonArea
import com.mesutemre.kutuphanem.kitap_detay.presentation.components.body.KitapYorumBodyArea
import com.mesutemre.kutuphanem.kitap_detay.presentation.components.bottomsheet.KitapDetayAciklamaBottomSheet
import com.mesutemre.kutuphanem.kitap_detay.presentation.components.bottomsheet.KitapYorumListeBottomSheet
import com.mesutemre.kutuphanem.kitap_detay.presentation.components.header.KitapDetayHeaderArea
import com.mesutemre.kutuphanem.model.ERROR
import com.mesutemre.kutuphanem.model.SUCCESS
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.normalUbuntuTransparentBold
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem.util.convertDate2String
import com.mesutemre.kutuphanem.util.customcomponents.snackbar.KutuphanemSnackBarHost
import com.mesutemre.kutuphanem.util.rememberKutuphanemAppState
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import com.mesutemre.kutuphanem_ui.card.KitapAciklamaText
import com.mesutemre.kutuphanem_ui.card.KitapDetayInfoCard
import com.mesutemre.kutuphanem_ui.extensions.rippleClick
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun KitapDetayScreen(
    navController: NavController,
    viewModel: KitapDetayScreenViewModel = hiltViewModel()
) {
    val detayAreaState = rememberLazyListState()
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorPalette.loginBackColor)
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
            }
            val isFirstItemVisible by remember {
                derivedStateOf {
                    mutableStateOf(
                        detayAreaState.firstVisibleItemIndex > 0
                    )
                }
            }
            val scrollOffset by remember {
                derivedStateOf {
                    mutableStateOf(
                        detayAreaState.firstVisibleItemScrollOffset
                    )
                }
            }
            AnimatedVisibility(visible = isFirstItemVisible.value, enter = fadeIn()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.sdp)
                        .background(color = MaterialTheme.colorPalette.lacivert),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colorPalette.white,
                        modifier = Modifier
                            .padding(top = 20.sdp, start = 8.sdp)
                            .size(24.sdp)
                            .rippleClick {
                                navController.popBackStack()
                            })
                }
            }
            when (state.value.kitapDetayItemResource) {
                is BaseResourceEvent.Success -> {
                    LazyColumn(state = detayAreaState) {
                        item {
                            if (!isFirstItemVisible.value) {
                                AnimatedVisibility(visible = !isFirstItemVisible.value) {
                                    KitapDetayHeaderArea(
                                        kitapDetayItemResource = state.value.kitapDetayItemResource
                                    )
                                }
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.fillMaxWidth())
                        }
                        item {
                            KitapIslemButtonArea()
                        }
                        item {
                            Text(
                                text = stringResource(id = R.string.kitap_detay_kitap_bilgi_label),
                                modifier = Modifier.padding(
                                    bottom = 8.sdp,
                                    start = 16.sdp,
                                    end = 16.sdp,
                                    top = 10.sdp
                                ),
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.normalUbuntuTransparentBold
                            )
                        }

                        val kitapDetayData = state.value.kitapDetayItemResource.data
                        item {
                            KitapDetayInfoCard(
                                label = stringResource(id = R.string.kitapAdLabelText),
                                value = kitapDetayData?.kitapAd ?: ""
                            )
                        }
                        item {
                            KitapDetayInfoCard(
                                label = stringResource(id = R.string.yazarAdLabelText),
                                value = kitapDetayData?.yazarAd ?: ""
                            )
                        }
                        item {
                            KitapDetayInfoCard(
                                label = stringResource(id = R.string.alinmaTarLabelText),
                                value = kitapDetayData?.alinmaTar?.convertDate2String() ?: ""
                            )
                        }
                        item {
                            KitapDetayInfoCard(
                                label = stringResource(id = R.string.kitapTurLabel),
                                value = kitapDetayData?.kitapTurAd ?: ""
                            )
                        }
                        item {
                            KitapDetayInfoCard(
                                label = stringResource(id = R.string.yayinEviLabel),
                                value = kitapDetayData?.yayinEviAd ?: ""
                            )
                        }
                        item {
                            KitapAciklamaText(
                                label = stringResource(id = R.string.aciklamaHintText),
                                aciklama = kitapDetayData?.kitapAciklama ?: ""
                            ) {
                                coroutineScope.launch {
                                    viewModel.onExpandKitapDetayBottomSheet(
                                        kitapDetayData?.kitapAciklama ?: ""
                                    )
                                    bottomSheetScaffoldState.bottomSheetState.animateTo(
                                        BottomSheetValue.Expanded,
                                        tween(500)
                                    )
                                }
                            }
                        }
                        item {
                            Text(
                                text = stringResource(id = R.string.kitap_detay_kitap_puanYorum_bilgi_label),
                                modifier = Modifier.padding(vertical = 8.sdp, horizontal = 16.sdp),
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.normalUbuntuTransparentBold
                            )
                        }
                        item {
                            KitapDetayRatingArea(kitapPuan = kitapDetayData?.kitapPuan ?: 0f)
                        }
                        item {
                            KitapYorumBodyArea(
                                yorumSayisi = kitapDetayData?.yorumSayisi ?: 0,
                                kitapDetayIlkYorumModel = kitapDetayData?.kitapIlkYorum,
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
            }
            /* KitapDetayInfoBodyArea(
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
            ) */
        }

    }
}