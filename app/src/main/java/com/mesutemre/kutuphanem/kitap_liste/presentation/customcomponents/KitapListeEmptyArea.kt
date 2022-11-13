package com.mesutemre.kutuphanem.kitap_liste.presentation.customcomponents

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.NotAccessible
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem_ui.theme.largeUbuntuTransparent
import com.mesutemre.kutuphanem_ui.theme.sdp

@Composable
fun KitapListeEmptyArea(message: String) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Column {
            Icon(
                imageVector = Icons.Filled.Archive,
                contentDescription = message,
                tint = MaterialTheme.colorPalette.transparent,
                modifier = Modifier.size(64.sdp).align(Alignment.CenterHorizontally)
            )
            Text(
                text = message,
                modifier = Modifier.padding(top = 16.sdp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.largeUbuntuTransparent
            )
        }

    }
}