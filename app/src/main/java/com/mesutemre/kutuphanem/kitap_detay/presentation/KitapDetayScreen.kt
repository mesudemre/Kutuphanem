package com.mesutemre.kutuphanem.kitap_detay.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.mesutemre.kutuphanem.kitap_detay.presentation.components.body.KitapDetayInfoBodyArea
import com.mesutemre.kutuphanem.kitap_detay.presentation.components.header.KitapDetayHeaderArea
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem_ui.theme.sdp

@Composable
fun KitapDetayScreen(
    showSnackbar: (String, SnackbarDuration, Int) -> Unit,
    viewModel: KitapDetayScreenViewModel = hiltViewModel()
) {
    val state: KitapDetayScreenState = viewModel.state.collectAsState().value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorPalette.loginBackColor)
    ) {
        KitapDetayHeaderArea(
            kitapDetayItemResource = state.kitapDetayItemResource
        )
        Spacer(modifier = Modifier.height(12.sdp))
        KitapDetayInfoBodyArea(kitapDetayItemResource = state.kitapDetayItemResource)
    }
}