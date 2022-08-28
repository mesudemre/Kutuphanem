package com.mesutemre.kutuphanem.dashboard.presentation.components.personelinfo

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.sdp

@Composable
fun DashBoardKullaniciBilgiLoading() {
    Row(
        modifier = Modifier.padding(start = 8.sdp, end = 8.sdp, top = 20.sdp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(
            modifier = Modifier
                .size(64.sdp)
                .clip(CircleShape)
                .border(2.sdp, MaterialTheme.colorPalette.shrim_gray, CircleShape)
                .placeholder(
                    visible = true,
                    color = MaterialTheme.colorPalette.placeHolderColor,
                    highlight = PlaceholderHighlight.shimmer(highlightColor = MaterialTheme.colorPalette.otherGrayLight)
                )
        )
        Column(modifier = Modifier.padding(start = 8.sdp)) {
            Spacer(
                modifier = Modifier
                    .width(132.sdp)
                    .height(8.sdp)
                    .clip(shape = MaterialTheme.shapes.medium)
                    .placeholder(
                        visible = true,
                        color = MaterialTheme.colorPalette.placeHolderColor,
                        highlight = PlaceholderHighlight.shimmer(highlightColor = MaterialTheme.colorPalette.otherGrayLight)
                    )
            )

            Spacer(
                modifier = Modifier
                    .width(180.sdp)
                    .height(8.sdp)
                    .clip(shape = MaterialTheme.shapes.medium)
                    .padding(top = 2.sdp)
                    .placeholder(
                        visible = true,
                        color = MaterialTheme.colorPalette.placeHolderColor,
                        highlight = PlaceholderHighlight.shimmer(highlightColor = MaterialTheme.colorPalette.otherGrayLight)
                    )
            )
        }

    }
}