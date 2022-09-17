package com.mesutemre.kutuphanem.dashboard.presentation.components.statistics

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.normalUbuntuTransparentBold
import com.mesutemre.kutuphanem.ui.theme.sdp

@Composable
fun StatisticsAreaTitle() {
    ConstraintLayout(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 8.sdp)) {
        val (labelText, iconItem) = createRefs()
        Text(text = stringResource(id = R.string.dasboard_statistics_title), modifier = Modifier
            .constrainAs(labelText) {
                top.linkTo(parent.top, 8.sdp)
                bottom.linkTo(parent.bottom, 8.sdp)
            }
            .padding(horizontal = 8.sdp),
            style = MaterialTheme.typography.normalUbuntuTransparentBold)

        Icon(
            Icons.Filled.ArrowForward,
            modifier = Modifier.constrainAs(iconItem) {
                top.linkTo(parent.top, 8.sdp)
                bottom.linkTo(parent.bottom, 8.sdp)
                end.linkTo(parent.end, 8.sdp)
            },
            contentDescription = stringResource(id = R.string.dasboard_statistics_title),
            tint = MaterialTheme.colorPalette.transparent
        )
    }
}