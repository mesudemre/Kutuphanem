package com.mesutemre.kutuphanem.kitap_detay.presentation.components.header

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent

@Composable
fun BoxScope.KitapDetayHeaderBackground(
    kitapResim: String,
    kitapAd: String
) {
    SubcomposeAsyncImage(model = kitapResim,
        modifier = Modifier
            .matchParentSize(),
        contentDescription = kitapAd,
        alpha = 0.1f,
        loading = {
            if (painter.state is AsyncImagePainter.State.Loading || painter.state is AsyncImagePainter.State.Error) {
                //TODO : Buraya bir loader eklenebilir
            } else {
                SubcomposeAsyncImageContent()
            }
        })
}