package com.mesutemre.kutuphanem.dashboard.presentation.components.statistics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem_ui.theme.smallAllegraBlackBold

@Composable
fun StatisticsDescriptionArea(
    description: String,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorPalette.white,
                shape = RoundedCornerShape(bottomStart = 6.sdp, bottomEnd = 6.sdp)
            )
            .clip(shape = RoundedCornerShape(bottomStart = 6.sdp, bottomEnd = 6.sdp))

    ) {
        Divider(
            modifier = Modifier.padding(vertical = 4.sdp),
            thickness = 1.sdp,
            color = MaterialTheme.colorPalette.otherGrayLight
        )
        Text(
            text = description,
            style = MaterialTheme.typography.smallAllegraBlackBold,
            textAlign = TextAlign.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.sdp)
        )
    }
}