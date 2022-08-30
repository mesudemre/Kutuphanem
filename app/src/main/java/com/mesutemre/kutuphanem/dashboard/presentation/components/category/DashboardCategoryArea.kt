package com.mesutemre.kutuphanem.dashboard.presentation.components.category

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.dashboard.domain.model.DashboardKategoriItem
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.sdp

@Composable
fun DashboardCategoryArea(kategoriResource: BaseResourceEvent<List<DashboardKategoriItem>>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.sdp)
            .padding(horizontal = 16.sdp, vertical = 16.sdp),
        border = BorderStroke(
            width = (1/2).sdp,
            color = MaterialTheme.colorPalette.transparent),
        shape = MaterialTheme.shapes.medium,
        backgroundColor = MaterialTheme.colorPalette.white,
        elevation = 8.sdp
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            DashboardCategoryAreaTitle()
            when(kategoriResource) {
                is BaseResourceEvent.Loading -> {
                    DashboardCategoryAreaLoading()
                }
                is BaseResourceEvent.Success-> {
                    DashboardCategoryList(kategoriResource.data!!)
                }
                is BaseResourceEvent.Error-> {

                }
            }
        }

    }
}