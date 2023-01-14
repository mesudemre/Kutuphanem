package com.mesutemre.kutuphanem.kitap_detay.presentation.components.header

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import com.mesutemre.kutuphanem.kitap_detay.domain.model.KitapDetayItem
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import com.mesutemre.kutuphanem_ui.theme.colorPalette

@Composable
fun KitapDetayHeaderArea(
    kitapDetayItemResource: BaseResourceEvent<KitapDetayItem>
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.sdp)
            .clip(
                shape = RoundedCornerShape(bottomStart = 32.sdp, bottomEnd = 32.sdp)
            )
            .background(
                brush = Brush.verticalGradient(
                    0.01f to MaterialTheme.colorPalette.lacivert.copy(0.05f),
                    0.1f to MaterialTheme.colorPalette.lacivert.copy(0.3f),
                    0.6f to MaterialTheme.colorPalette.lacivert,
                    startY = Float.POSITIVE_INFINITY,
                    endY = 0.0f,
                )
            )
    ) {
        when (kitapDetayItemResource) {
            is BaseResourceEvent.Loading -> {
                KitapDetayHeaderLoading()
            }
            is BaseResourceEvent.Success -> {
                KitapDetayHeaderBackground(
                    kitapResim = kitapDetayItemResource.data!!.kitapResim,
                    kitapAd = kitapDetayItemResource.data!!.kitapAd
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.sdp, start = 16.sdp, end = 16.sdp)
                ) {
                    KitapDetayHeaderContent(
                        kitapResim = kitapDetayItemResource.data!!.kitapResim,
                        kitapAd = kitapDetayItemResource.data!!.kitapAd
                    )
                }
            }
            else -> {}
        }
    }
}