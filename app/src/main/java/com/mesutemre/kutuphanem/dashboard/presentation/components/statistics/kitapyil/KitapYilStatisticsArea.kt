package com.mesutemre.kutuphanem.dashboard.presentation.components.statistics.kitapyil

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.dashboard.domain.model.DashBoardKitapYilIstatistikItem
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem.util.customcomponents.error.KutuphanemErrorView
import com.mesutemre.kutuphanem.util.customcomponents.progressbar.KutuphanemShimmerArea
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import com.mesutemre.kutuphanem_ui.card.KutuphanemCardTitle
import com.mesutemre.kutuphanem_ui.chart.Kutuphanem3dBarChart
import com.mesutemre.kutuphanem_ui.chart.KutuphanemBarChartInput

@Composable
fun KitapYilStatisticsArea(
    kitapYilIstatistikResource: BaseResourceEvent<List<DashBoardKitapYilIstatistikItem>>,
    kitapYilIstatistikList: List<KutuphanemBarChartInput>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.sdp)
            .padding(start = 16.sdp, end = 16.sdp, top = 16.sdp, bottom = 32.sdp),
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
                    Kutuphanem3dBarChart(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 12.sdp, vertical = 32.sdp),
                        inputList = kitapYilIstatistikList
                    )
                }
                is BaseResourceEvent.Error -> {
                    KutuphanemErrorView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.sdp),
                        errorText = kitapYilIstatistikResource.message ?: stringResource(
                            id = R.string.dasboard_category_statistics_kitap_yil_error
                        )
                    )
                }
                else -> {}
            }
        }
    }
}