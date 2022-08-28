package com.mesutemre.kutuphanem.dashboard.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.dashboard.domain.model.DashboardKullaniciBilgiData
import com.mesutemre.kutuphanem.dashboard.presentation.components.personelinfo.DashBoardKullaniciBilgi
import com.mesutemre.kutuphanem.dashboard.presentation.components.personelinfo.DashBoardKullaniciBilgiLoading
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.sdp

@Composable
fun PersonelInfoArea(kullaniciBilgi: BaseResourceEvent<DashboardKullaniciBilgiData>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.sdp)
            .clip(
                shape = RoundedCornerShape(bottomStart = 32.sdp, bottomEnd = 32.sdp)
            )
            .background(color = MaterialTheme.colorPalette.lacivert)
    ) {
        when(kullaniciBilgi) {
            is BaseResourceEvent.Loading -> {
                DashBoardKullaniciBilgiLoading()
            }
            is BaseResourceEvent.Success -> {
                DashBoardKullaniciBilgi(kullaniciBilgi.data!!)
            }
            is BaseResourceEvent.Error -> {

            }
        }
    }
}
