package com.mesutemre.kutuphanem.dashboard.presentation.components.category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.dashboard.domain.model.DashboardKategoriItem
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem.util.customcomponents.progressbar.KutuphanemShimmerArea
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import com.mesutemre.kutuphanem_ui.card.KutuphanemCardTitle

@Composable
fun DashboardCategoryArea(kategoriResource: BaseResourceEvent<List<DashboardKategoriItem>>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.sdp)
            .padding(horizontal = 16.sdp),
        shape = MaterialTheme.shapes.medium,
        backgroundColor = MaterialTheme.colorPalette.white,
        elevation = 8.sdp
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            KutuphanemCardTitle(title = R.string.dashKategoriText)
            when(kategoriResource) {
                is BaseResourceEvent.Loading -> {
                    KutuphanemShimmerArea(height = 120)
                }
                is BaseResourceEvent.Success-> {
                    DashboardCategoryList(kategoriResource.data!!)
                }
                is BaseResourceEvent.Error-> {
                    DashboardCategoryAreaError(errorMessage = kategoriResource.message ?: stringResource(
                        id = R.string.dashKategoriListeHata
                    ))
                }
            }
        }
    }
}