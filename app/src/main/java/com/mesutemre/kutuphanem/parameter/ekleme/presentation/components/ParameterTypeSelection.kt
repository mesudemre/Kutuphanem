package com.mesutemre.kutuphanem.parameter.ekleme.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem.ui.theme.smallAllegraBlack
import com.mesutemre.kutuphanem.ui.theme.smallAllegraWhiteBold

enum class SelectedParameterType {
    YAYINEVI, KITAPTUR
}

@Composable
fun ParameterTypeSelection(
    modifier: Modifier,
    selectedParameterType: SelectedParameterType,
    onSelectType: (SelectedParameterType) -> Unit
) {

    Row(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .height(20.sdp)
                .weight(0.5f)
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.sdp,0.sdp,0.sdp,4.sdp))
                .border(
                    BorderStroke(1.sdp, MaterialTheme.colorPalette.primaryTextColor),
                    shape = RoundedCornerShape(4.sdp,0.sdp,0.sdp,4.sdp)
                )
                .background(if (selectedParameterType == SelectedParameterType.YAYINEVI) MaterialTheme.colorPalette.primaryTextColor else MaterialTheme.colorPalette.white)
                .clickable {
                    onSelectType(SelectedParameterType.YAYINEVI)
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.yayinEviLabel),
                modifier = Modifier
                    .align(Alignment.Center),
                style = if (selectedParameterType == SelectedParameterType.YAYINEVI) MaterialTheme.typography.smallAllegraWhiteBold else MaterialTheme.typography.smallAllegraBlack,
                textAlign = TextAlign.Center,
            )

        }
        Box(
            modifier = Modifier
                .height(20.sdp)
                .clip(RoundedCornerShape(0.sdp,4.sdp,4.sdp,0.sdp))
                .fillMaxWidth()
                .weight(0.5f)
                .border(
                    BorderStroke(1.sdp, MaterialTheme.colorPalette.primaryTextColor),
                    shape = RoundedCornerShape(0.sdp,4.sdp,4.sdp,0.sdp)
                )
                .background(if (selectedParameterType == SelectedParameterType.KITAPTUR) MaterialTheme.colorPalette.primaryTextColor else MaterialTheme.colorPalette.white)
                .clickable {
                    onSelectType(SelectedParameterType.KITAPTUR)
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.kitapTurLabel),
                modifier = Modifier
                    .align(Alignment.Center),
                style = if (selectedParameterType == SelectedParameterType.KITAPTUR) MaterialTheme.typography.smallAllegraWhiteBold else MaterialTheme.typography.smallAllegraBlack,
                textAlign = TextAlign.Center,
            )
        }

    }
}