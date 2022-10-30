package com.mesutemre.kutuphanem.kitap_liste.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mesutemre.kutuphanem.kitap_liste.presentation.customcomponents.KutuphanemListChipArea
import com.mesutemre.kutuphanem.kitap_liste.presentation.customcomponents.TumKitapListe
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.sdp

@Composable
fun KitapListeScreen(
    navController: NavHostController,
    viewModel: KitapListViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.sdp)
            .background(color = MaterialTheme.colorPalette.loginBackColor)
    ) {
        KutuphanemListChipArea(selectedListType = state.selectedListType, onSelect = {
            viewModel.setSelectedListType(it)
        })
        Divider(
            modifier = Modifier.padding(bottom = 4.sdp),
            thickness = 1.sdp,
            color = MaterialTheme.colorPalette.otherGrayLight
        )
        TumKitapListe(kitapServiceListeSource = state.kitapServiceListeSource)
    }
}
