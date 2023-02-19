package com.mesutemre.kutuphanem.dashboard.presentation.components.statistics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.dashboard.domain.model.DashboardKitapTurIstatistikItem
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem.util.customcomponents.error.KutuphanemErrorView
import com.mesutemre.kutuphanem.util.customcomponents.progressbar.KutuphanemShimmerArea
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import com.mesutemre.kutuphanem_ui.card.KutuphanemCardTitle
import com.mesutemre.kutuphanem_ui.chart.KutuphanemPieChart
import com.mesutemre.kutuphanem_ui.chart.KutuphanemPieChartInput

@Composable
fun StatisticsArea(
    kitapTurIstatistikResource: BaseResourceEvent<List<DashboardKitapTurIstatistikItem>>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(270.sdp)
            .padding(start = 16.sdp, end = 16.sdp, top = 8.sdp),
        shape = RoundedCornerShape(topStart = 6.sdp, topEnd = 6.sdp),
        backgroundColor = MaterialTheme.colorPalette.white,
        elevation = 8.sdp
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            KutuphanemCardTitle(title = R.string.dasboard_category_statistics_title)
            when (kitapTurIstatistikResource) {
                is BaseResourceEvent.Loading -> {
                    KutuphanemShimmerArea(height = 180)
                }
                is BaseResourceEvent.Success -> {
                    val chartInputList = remember {
                        derivedStateOf {
                            mutableStateOf(
                                kitapTurIstatistikResource.data?.sortedByDescending {
                                    it.adet
                                }?.map {
                                    KutuphanemPieChartInput(
                                        value = it.adet.toInt(),
                                        description = it.aciklama,
                                        color = Color((Math.random() * 16777215).toInt() or (0xFF shl 24))
                                    )
                                }?.take(5)
                            )
                        }.value
                    }

                    KutuphanemPieChart(
                        modifier = Modifier.fillMaxWidth(),
                        input = chartInputList.value!!
                    )
                }
                is BaseResourceEvent.Error -> {
                    KutuphanemErrorView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.sdp),
                        errorText = kitapTurIstatistikResource.message ?: stringResource(
                            id = R.string.dasboard_category_statistics_error
                        )
                    )
                }
                else -> {}
            }
        }
    }
}