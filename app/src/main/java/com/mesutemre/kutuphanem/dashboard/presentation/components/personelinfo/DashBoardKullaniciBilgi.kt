package com.mesutemre.kutuphanem.dashboard.presentation.components.personelinfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
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
        Image(
            painter = painterResource(R.drawable.kutuphane),
            contentDescription = "avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(64.sdp)
                .clip(CircleShape)
                .border(2.sdp, MaterialTheme.colorPalette.shrim_gray, CircleShape)
        )
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