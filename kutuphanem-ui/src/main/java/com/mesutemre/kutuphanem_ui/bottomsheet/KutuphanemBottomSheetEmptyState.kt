package com.mesutemre.kutuphanem_ui.bottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.mesutemre.kutuphanem_ui.theme.colorPalette
import com.mesutemre.kutuphanem_ui.theme.normalUbuntuTransparent
import com.mesutemre.kutuphanem_ui.theme.sdp

@Composable
fun KutuphanemBottomSheetEmptyState(
    aciklama: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.sdp, vertical = 12.sdp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Filled.Search,
            contentDescription = aciklama,
            modifier = Modifier.size(32.sdp),
            tint = MaterialTheme.colorPalette.secondaryGray
        )
        Text(
            text = aciklama,
            style = MaterialTheme.typography.normalUbuntuTransparent,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 16.sdp)
        )
    }
}