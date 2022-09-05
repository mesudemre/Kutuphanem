package com.mesutemre.kutuphanem.dashboard.presentation.components.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.mediumUbuntuWhiteBold

@Composable
fun DashboardCategoryAreaError(errorMessage:String) {
    Box(modifier = Modifier.fillMaxWidth().background(color = MaterialTheme.colorPalette.kirmizi), contentAlignment = Alignment.Center) {
        Text(
            text = errorMessage,
            style = MaterialTheme.typography.mediumUbuntuWhiteBold,
            textAlign = TextAlign.Center
        )
    }
}