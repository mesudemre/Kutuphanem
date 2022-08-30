package com.mesutemre.kutuphanem.dashboard.presentation.components.category

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.mesutemre.kutuphanem.dashboard.domain.model.DashboardKategoriItem
import com.mesutemre.kutuphanem.dashboard.presentation.components.personelinfo.KullaniciImageIndeedLoading
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem.ui.theme.smallUbuntuTransparentBold

@Composable
fun DashboardCategoryItem(item: DashboardKategoriItem) {
    Column(
        modifier = Modifier.padding(horizontal = 8.sdp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SubcomposeAsyncImage(model = item.resim,
            modifier = Modifier
                .size(64.sdp)
                .clip(CircleShape)
                .border(2.sdp, MaterialTheme.colorPalette.transparent, CircleShape),
            contentDescription = item.aciklama,
            loading = {
                if (painter.state is AsyncImagePainter.State.Loading || painter.state is AsyncImagePainter.State.Error) {
                    KullaniciImageIndeedLoading()
                } else {
                    SubcomposeAsyncImageContent()
                }
            })

        Text(
            text = item.aciklama,
            style = MaterialTheme.typography.smallUbuntuTransparentBold,
            modifier = Modifier.padding(top = 8.sdp)
        )
    }
}