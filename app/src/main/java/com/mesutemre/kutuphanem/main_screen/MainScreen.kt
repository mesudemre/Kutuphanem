package com.mesutemre.kutuphanem.main_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.mediumAllegraLacivertBold

@Composable
fun MainScreen() {
    Box(modifier = Modifier
        .background(color = MaterialTheme.colorPalette.loginBackColor)
        .fillMaxSize(),contentAlignment = Alignment.Center) {
        Text(
            text = "Main Screen!!!",
            style = MaterialTheme.typography.mediumAllegraLacivertBold
        )
    }
}