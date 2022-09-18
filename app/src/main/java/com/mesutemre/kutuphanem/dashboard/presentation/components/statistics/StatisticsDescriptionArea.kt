package com.mesutemre.kutuphanem.dashboard.presentation.components.statistics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.mesutemre.kutuphanem.dashboard.domain.model.DashboardKitapTurIstatistikItem
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem.ui.theme.smallUbuntuTransparentBold
import com.mesutemre.kutuphanem.util.getColorListForPieChart

@Composable
fun StatisticsDescriptionArea(
    list: List<DashboardKitapTurIstatistikItem>
) {
    LazyRow(modifier = Modifier.fillMaxSize().padding(horizontal = 16.sdp)) {
        itemsIndexed(list) { index, item ->
            StatisticsDescriptionAreaItem(
                color = getColorListForPieChart()[index],
                aciklama = item.aciklama
            )
        }
    }
}

@Composable
fun StatisticsDescriptionAreaItem(color: Color, aciklama: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(start = 4.sdp),verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .width(16.sdp)
                .height(12.sdp)
                .clip(shape = RoundedCornerShape(2.sdp))
                .background(color = color)
        )
        Text(
            text = aciklama,
            style = MaterialTheme.typography.smallUbuntuTransparentBold,
            modifier = Modifier.padding(start = 4.sdp)
        )
    }
}