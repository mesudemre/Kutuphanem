package com.mesutemre.kutuphanem.kitap_liste.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarDuration
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.mesutemre.kutuphanem.kitap_liste.domain.model.SelectedListType
import com.mesutemre.kutuphanem.kitap_liste.presentation.customcomponents.KitapArsivListe
import com.mesutemre.kutuphanem.kitap_liste.presentation.customcomponents.KutuphanemListChipArea
import com.mesutemre.kutuphanem.kitap_liste.presentation.customcomponents.TumKitapListe
import com.mesutemre.kutuphanem.navigation.KutuphanemNavigationItem
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem_ui.extensions.isScrollingUp

@Composable
fun KitapListeScreen(
    navController: NavHostController,
    showSnackbar: (String, SnackbarDuration, Int) -> Unit,
    viewModel: KitapListViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    val tumKitapListeLazyState = rememberLazyListState()
    val arsivKitapListeLazyState = rememberLazyListState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.sdp)
            .background(color = MaterialTheme.colorPalette.loginBackColor)
    ) {
        AnimatedVisibility(
            (state.selectedListType == SelectedListType.TUM_LISTE && tumKitapListeLazyState.isScrollingUp())
                    ||
                    (state.selectedListType == SelectedListType.ARSIV && arsivKitapListeLazyState.isScrollingUp())
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                KutuphanemListChipArea(selectedListType = state.selectedListType, onSelect = {
                    viewModel.setSelectedListType(it)
                })
                Divider(
                    modifier = Modifier.padding(bottom = 4.sdp),
                    thickness = 1.sdp,
                    color = MaterialTheme.colorPalette.otherGrayLight
                )
            }
        }

        when (state.selectedListType.ordinal) {
            SelectedListType.TUM_LISTE.ordinal -> {
                TumKitapListe(
                    listState = tumKitapListeLazyState,
                    kitapServiceListeSource = state.kitapListItemPageData.collectAsLazyPagingItems(),
                    kitapIslemSource = state.kitapIslemSource,
                    kitapShareSource = state.kitapShareSource,
                    showSnackbar = showSnackbar,
                    onClickKitapLike = {
                        viewModel.kitapBegen(it)
                    },
                    onClickKitapShare = { kitapId, kitapAd, yazarAd, kitapResim ->
                        viewModel.kitapPaylas(kitapId, kitapAd, yazarAd, kitapResim)
                    }
                ) { kitapId ->
                    navController.navigate(
                        KutuphanemNavigationItem.KitapDetayScreen.screenRoute.replace(
                            "{kitapId}",
                            kitapId.toString()
                        )
                            .replace("{isFromArsiv}", false.toString())
                    )
                }
            }
            SelectedListType.ARSIV.ordinal -> {
                KitapArsivListe(
                    arsivListState = arsivKitapListeLazyState,
                    kitapArsivListeSource = state.kitapArsivListeSource
                )
            }
            SelectedListType.BEGENDIKLERIM.ordinal -> {
                //TODO : Beğeni listesinin servisi format esnasında yedeklenmediği için şimdilik bırakıldı...
            }
        }
    }
}
