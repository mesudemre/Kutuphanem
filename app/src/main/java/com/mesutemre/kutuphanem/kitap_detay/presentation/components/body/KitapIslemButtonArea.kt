package com.mesutemre.kutuphanem.kitap_detay.presentation.components.body

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem_ui.extensions.rippleClick
import com.mesutemre.kutuphanem_ui.theme.colorPalette
import com.mesutemre.kutuphanem_ui.theme.smallAllegraBlack

@Composable
fun KitapIslemButtonArea() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.sdp, vertical = 12.sdp),
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(
            width = 1.sdp, brush = Brush.horizontalGradient(
                listOf(
                    MaterialTheme.colorPalette.acikTuruncu,
                    MaterialTheme.colorPalette.fistikYesil,
                    MaterialTheme.colorPalette.acikLacivert,
                    MaterialTheme.colorPalette.lacivert
                ),
                tileMode = TileMode.Repeated
            )
        ),
        elevation = 4.sdp,
        backgroundColor = MaterialTheme.colorPalette.white
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.sdp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            KitapIslemButton(icon = {
                Icon(
                    imageVector = Icons.Outlined.Share,
                    contentDescription = stringResource(id = R.string.shareLabel),
                    modifier = Modifier
                        .padding(start = 8.sdp)
                        .size(16.sdp)
                        .rippleClick { },
                    tint = MaterialTheme.colorPalette.transparent
                )
            }, label = stringResource(id = R.string.shareLabel))

            Spacer(modifier = Modifier.width(8.sdp))

            KitapIslemButton(icon = {
                Icon(
                    imageVector = ImageVector.vectorResource(id = com.mesutemre.kutuphanem_ui.R.drawable.icon_like),
                    contentDescription = stringResource(id = R.string.begenLabel),
                    modifier = Modifier
                        .padding(start = 8.sdp)
                        .size(16.sdp)
                        .rippleClick { },
                    tint = MaterialTheme.colorPalette.transparent
                )
            }, label = stringResource(id = R.string.begenLabel))

            Spacer(modifier = Modifier.width(8.sdp))

            KitapIslemButton(icon = {
                Icon(
                    imageVector = Icons.Outlined.Download,
                    contentDescription = stringResource(id = R.string.archiveLabel),
                    modifier = Modifier
                        .padding(start = 8.sdp)
                        .size(16.sdp)
                        .rippleClick { },
                    tint = MaterialTheme.colorPalette.transparent
                )
            }, label = stringResource(id = R.string.archiveLabel))
        }
    }
}

@Composable
private fun KitapIslemButton(
    icon: @Composable () -> Unit,
    label: String
) {
    Card(
        modifier = Modifier
            .height(32.sdp),
        backgroundColor = MaterialTheme.colorPalette.white,
        shape = MaterialTheme.shapes.small,
        border = BorderStroke(
            width = (1 / 2).sdp, color = MaterialTheme.colorPalette.transparent
        )
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            icon()
            Text(
                text = label,
                modifier = Modifier.padding(start = 4.sdp, end = 8.sdp),
                style = MaterialTheme.typography.smallAllegraBlack
            )
        }
    }
}