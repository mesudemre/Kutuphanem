package com.mesutemre.kutuphanem.dashboard.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.dashboard.domain.model.DashboardKullaniciBilgiData
import com.mesutemre.kutuphanem.dashboard.presentation.components.personelinfo.DashBoardKullaniciBilgi
import com.mesutemre.kutuphanem.dashboard.presentation.components.personelinfo.DashBoardKullaniciBilgiLoading
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.mediumUbuntuWhiteBold
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent

@Composable
fun PersonelInfoArea(kullaniciBilgi: BaseResourceEvent<DashboardKullaniciBilgiData>) {
    when(kullaniciBilgi) {
        is BaseResourceEvent.Loading -> {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.sdp)
                    .clip(
                        shape = RoundedCornerShape(bottomStart = 32.sdp, bottomEnd = 32.sdp)
                    )
                    .background(color = MaterialTheme.colorPalette.lacivert)
            ) {
                DashBoardKullaniciBilgiLoading()
            }
        }
        is BaseResourceEvent.Success -> {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.sdp)
                    .clip(
                        shape = RoundedCornerShape(bottomStart = 32.sdp, bottomEnd = 32.sdp)
                    )
                    .background(color = MaterialTheme.colorPalette.lacivert)
            ) {
                DashBoardKullaniciBilgi(kullaniciBilgi.data!!)
            }

        }
        is BaseResourceEvent.Error -> {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.sdp)
                    .clip(
                        shape = RoundedCornerShape(bottomStart = 32.sdp, bottomEnd = 32.sdp)
                    )
                    .background(color = MaterialTheme.colorPalette.kirmizi),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = kullaniciBilgi.message ?: stringResource(id = R.string.profilSayfaHata),
                    style = MaterialTheme.typography.mediumUbuntuWhiteBold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
