package com.mesutemre.kutuphanem.kitap_liste.presentation.customcomponents

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.sdp

@Composable
fun KitapListeLoading() {
    LazyColumn(modifier = Modifier.padding(vertical = 16.sdp, horizontal = 16.sdp)) {
        items(25) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.sdp)
                    .clip(shape = MaterialTheme.shapes.medium)
                    .placeholder(
                        visible = true,
                        color = MaterialTheme.colorPalette.placeHolderColor,
                        highlight = PlaceholderHighlight.shimmer(highlightColor = MaterialTheme.colorPalette.otherGrayLight)
                    )
            )
        }
    }
}

