package com.mesutemre.kutuphanem.kitap_detay.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.mesutemre.kutuphanem.kitap_detay.presentation.components.header.KitapDetayHeaderArea
import com.mesutemre.kutuphanem.ui.theme.colorPalette

@Composable
fun KitapDetayScreen(
    showSnackbar: (String, SnackbarDuration, Int) -> Unit,
    viewModel: KitapDetayScreenViewModel = hiltViewModel()
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorPalette.loginBackColor)
    ) {
        KitapDetayHeaderArea(
            kitapResim = "http://192.168.1.105:8080/KutuphaneSistemiWS/api/kitap/resim/16",
            kitapAd = "Yol Ayrımı"
        )
    }
}