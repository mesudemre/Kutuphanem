package com.mesutemre.kutuphanem_ui.bottomsheet

import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.mesutemre.kutuphanem_ui.theme.colorPalette
import com.mesutemre.kutuphanem_ui.theme.sdp

@Composable
fun KutuphanemBottomSheetGesturer() {
    Divider(
        modifier = Modifier
            .width(32.sdp)
            .clip(shape = MaterialTheme.shapes.small),
        thickness = 4.sdp,
        color = MaterialTheme.colorPalette.secondaryGray
    )
}