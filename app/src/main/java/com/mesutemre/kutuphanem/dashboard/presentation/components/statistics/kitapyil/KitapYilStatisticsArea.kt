package com.mesutemre.kutuphanem.dashboard.presentation.components.statistics.kitapyil

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.dashboard.domain.model.DashBoardKitapYilIstatistikItem
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem.util.customcomponents.card.KutuphanemCardTitle
import com.mesutemre.kutuphanem.util.customcomponents.chart.KutuphanemBarChart
import com.mesutemre.kutuphanem.util.customcomponents.progressbar.KutuphanemShimmerArea

@Composable
fun KitapYilStatisticsArea(
    kitapYilIstatistikResource: BaseResourceEvent<List<DashBoardKitapYilIstatistikItem>>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.sdp)
            .padding(horizontal = 16.sdp, vertical = 16.sdp),
        shape = MaterialTheme.shapes.medium,
        backgroundColor = MaterialTheme.colorPalette.white,
        elevation = 8.sdp
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            KutuphanemCardTitle(title = R.string.dasboard_category_statistics_kitap_yil_title)
            when (kitapYilIstatistikResource) {
                is BaseResourceEvent.Loading -> {
                    KutuphanemShimmerArea(height = 180)
                }
                is BaseResourceEvent.Success -> {
                    var showChart by remember {
                        mutableStateOf(true)
                    }
                    val chartData = remember {
                        derivedStateOf {
                            kitapYilIstatistikResource.data!!
                                .sortedBy {
                                    it.aciklama.toInt()
                                }
                                .takeLast(5)
                                .map {
                                it.aciklama to it.adet
                            }
                                .toMap()
                        }
                    }
                    KutuphanemBarChart(
                        data = chartData.value, height = 200.sdp,
                        isExpanded = showChart,
                        barColor = MaterialTheme.colorPalette.primaryTextColor
                    ) {
                        showChart = !showChart
                    }
                }
                is BaseResourceEvent.Error -> {

                }
            }
        }
    }
}