package com.mesutemre.kutuphanem.kitap_detay.presentation.components.header

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem_ui.theme.colorPalette
import com.mesutemre.kutuphanem_ui.theme.mediumUbuntuWhiteBold
import com.mesutemre.kutuphanem_ui.theme.ssp

@Composable
fun KitapDetayHeaderContent(
    kitapResim: String,
    kitapAd: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.sdp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SubcomposeAsyncImage(model = kitapResim,
            modifier = Modifier
                .width(100.sdp)
                .height(150.sdp),
            contentDescription = kitapAd,
            loading = {
                if (painter.state is AsyncImagePainter.State.Loading || painter.state is AsyncImagePainter.State.Error) {

                } else {
                    SubcomposeAsyncImageContent()
                }
            })
        Text(
            text = kitapAd,
            modifier = Modifier.padding(start = 8.sdp),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.mediumUbuntuWhiteBold.copy(
                lineHeight = 18.ssp
            )
        )
    }
}