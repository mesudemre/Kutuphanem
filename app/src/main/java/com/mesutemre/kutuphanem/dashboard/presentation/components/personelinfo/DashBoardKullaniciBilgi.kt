package com.mesutemre.kutuphanem.dashboard.presentation.components.personelinfo

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.dashboard.domain.model.DashboardKullaniciBilgiData
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.normalUbuntuWhite
import com.mesutemre.kutuphanem.ui.theme.normalUbuntuWhiteBold
import com.mesutemre.kutuphanem.ui.theme.sdp

@Composable
fun DashBoardKullaniciBilgi(kullaniciBilgi: DashboardKullaniciBilgiData) {
    Row(
        modifier = Modifier.padding(start = 8.sdp,end = 8.sdp,top = 20.sdp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SubcomposeAsyncImage(model = kullaniciBilgi.resim,
            modifier = Modifier
                .size(64.sdp)
                .clip(CircleShape)
                .border(2.sdp, MaterialTheme.colorPalette.fistikYesil, CircleShape),
            contentDescription = kullaniciBilgi.adSoyad,
            loading = {
                if (painter.state is AsyncImagePainter.State.Loading || painter.state is AsyncImagePainter.State.Error) {
                    KullaniciImageIndeedLoading()
                } else {
                    SubcomposeAsyncImageContent()
                }
            })
        Column(modifier = Modifier.padding(start = 8.sdp)) {
            Text(
                text = stringResource(id = R.string.dashboard_welcome_text),
                style = MaterialTheme.typography.normalUbuntuWhite
            )
            Text(
                text = kullaniciBilgi.adSoyad,
                modifier = Modifier.padding(top = 2.sdp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.normalUbuntuWhiteBold
            )
        }
    }
}

@Composable
fun KullaniciImageIndeedLoading() {
    Spacer(
        modifier = Modifier
            .width(132.sdp)
            .height(8.sdp)
            .clip(shape = MaterialTheme.shapes.medium)
            .placeholder(
                visible = true,
                color = MaterialTheme.colorPalette.placeHolderColor,
                highlight = PlaceholderHighlight.shimmer(highlightColor = MaterialTheme.colorPalette.otherGrayLight)
            )
    )
}