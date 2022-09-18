package com.mesutemre.kutuphanem.dashboard.presentation.components.statistics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.dashboard.domain.model.DashboardKitapTurIstatistikItem
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem.util.customcomponents.chart.KutuphanemPieChart
import com.mesutemre.kutuphanem.util.customcomponents.progressbar.KutuphanemShimmerArea

@Composable
fun StatisticsArea(
    kitapTurIstatistikResource: BaseResourceEvent<List<DashboardKitapTurIstatistikItem>>
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.sdp)
            .padding(horizontal = 16.sdp, vertical = 16.sdp),
        shape = MaterialTheme.shapes.medium,
        backgroundColor = MaterialTheme.colorPalette.white,
        elevation = 8.sdp
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            StatisticsAreaTitle()
            when(kitapTurIstatistikResource) {
                is BaseResourceEvent.Loading -> {
                    KutuphanemShimmerArea(height = 180)
                }
                is BaseResourceEvent.Success -> {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        KutuphanemPieChart(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 4.sdp),
                            pointList = kitapTurIstatistikResource.data!!.map {
                                it.adet
                            })
                        StatisticsDescriptionArea(list = kitapTurIstatistikResource.data!!)
                    }
                }
                is BaseResourceEvent.Error -> {

                }
            }
        }
    }
}