package com.mesutemre.kutuphanem.util.customcomponents.progressbar

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.sdp

@Composable
fun KutuphanemShimmerArea(height: Int) {
    Spacer(
        modifier = Modifier
            .height(height.sdp)
            .fillMaxWidth()
            .placeholder(
                visible = true,
                color = MaterialTheme.colorPalette.shrim_gray,
                highlight = PlaceholderHighlight
                    .shimmer(highlightColor = MaterialTheme.colorPalette.otherGrayLight)
            )
    )
}