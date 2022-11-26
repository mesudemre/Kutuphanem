package com.mesutemre.kutuphanem.kitap_detay.presentation.components.header

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem_ui.extensions.shimmerEffect

@Composable
fun KitapDetayHeaderLoading() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.sdp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(
            modifier = Modifier
                .width(100.sdp)
                .height(150.sdp)
                .border(
                    1.sdp,
                    MaterialTheme.colorPalette.secondaryGray,
                    MaterialTheme.shapes.medium
                )
                .shimmerEffect()
        )
        Spacer(
            modifier = Modifier
                .padding(start = 8.sdp)
                .width(60.sdp)
                .height(8.sdp)
                .clip(shape = MaterialTheme.shapes.medium)
                .shimmerEffect()
        )
        Spacer(
            modifier = Modifier
                .padding(start = 8.sdp, top = 4.sdp)
                .width(40.sdp)
                .height(8.sdp)
                .clip(shape = MaterialTheme.shapes.medium)
                .shimmerEffect()
        )
    }
}