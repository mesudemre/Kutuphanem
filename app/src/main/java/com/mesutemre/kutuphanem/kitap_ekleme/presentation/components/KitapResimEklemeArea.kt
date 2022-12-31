package com.mesutemre.kutuphanem.kitap_ekleme.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem_ui.theme.colorPalette
import com.mesutemre.kutuphanem_ui.theme.sdp
import com.mesutemre.kutuphanem_ui.theme.smallUbuntuError

@Composable
fun KitapResimEklemeArea(
    errorValidationMessage: String? = null
) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Card(
            modifier = Modifier
                .size(128.sdp)
                .clickable {

                },
            shape = MaterialTheme.shapes.medium,
            elevation = 8.sdp,
            border = BorderStroke(
                width = errorValidationMessage?.let {
                    1.sdp
                } ?: run {
                    (1 / 2).sdp
                },
                color = errorValidationMessage?.let {
                    MaterialTheme.colorPalette.kirmizi
                } ?: run {
                    MaterialTheme.colorPalette.transparent
                }
            ),
            backgroundColor = MaterialTheme.colorPalette.white,
            contentColor = MaterialTheme.colorPalette.white
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Icon(
                    Icons.Filled.PhotoCamera,
                    modifier = Modifier
                        .alpha(0.8f)
                        .size(100.sdp),
                    contentDescription = stringResource(id = R.string.kitapEklemeResimArea),
                    tint = MaterialTheme.colorPalette.secondaryGray
                )
            }
        }
        errorValidationMessage?.let {
            Text(
                modifier = Modifier
                    .padding(top = 8.sdp),
                text = it,
                style = MaterialTheme.typography.smallUbuntuError
            )
        }
    }
}