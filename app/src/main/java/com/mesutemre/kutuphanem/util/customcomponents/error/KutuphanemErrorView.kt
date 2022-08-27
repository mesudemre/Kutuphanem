package com.mesutemre.kutuphanem.util.customcomponents.error

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.mediumUbuntuErrorBold
import com.mesutemre.kutuphanem.ui.theme.sdp

@Composable
fun KutuphanemErrorView(modifier:Modifier,errorText: String) {
    Card(
        modifier = modifier,
        backgroundColor = MaterialTheme.colorPalette.white,
        shape = MaterialTheme.shapes.small,
        elevation = 2.sdp
    ) {
        Column{
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Icon(
                    Icons.Filled.Error,
                    modifier = Modifier.height(40.sdp).width(40.sdp),
                    contentDescription = errorText,
                    tint = MaterialTheme.colorPalette.acik_kirmizi
                )
            }

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(
                    text = errorText,
                    style = MaterialTheme.typography.mediumUbuntuErrorBold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}