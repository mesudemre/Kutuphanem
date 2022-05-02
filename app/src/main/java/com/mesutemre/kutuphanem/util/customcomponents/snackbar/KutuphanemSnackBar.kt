package com.mesutemre.kutuphanem.util.customcomponents.snackbar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.mesutemre.kutuphanem.model.ERROR
import com.mesutemre.kutuphanem.model.SUCCESS
import com.mesutemre.kutuphanem.model.WARNING
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem.ui.theme.smallUbuntuWhiteBold

@Composable
fun KutuphanemSnackBarHost(
    state: KutuphanemSnackbarState
) {
    SnackbarHost(
        hostState = state.snackbarHostState
    ) {
        Card(
            shape = MaterialTheme.shapes.medium,
            backgroundColor = when (state.currentSnackbarType) {
                SUCCESS ->
                    MaterialTheme.colorPalette.fistikYesil
                WARNING ->
                    MaterialTheme.colorPalette.acikTuruncu
                ERROR ->
                    MaterialTheme.colorPalette.kirmizi
                else -> MaterialTheme.colorPalette.fistikYesil
            },
            modifier = Modifier
                .padding(start = 16.sdp, end = 16.sdp, bottom = 24.sdp)
                .height(50.sdp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Icon(
                    when (state.currentSnackbarType) {
                        SUCCESS ->
                            Icons.Filled.Check
                        WARNING ->
                            Icons.Filled.Warning
                        ERROR ->
                            Icons.Filled.Error
                        else ->
                            Icons.Filled.Check
                    }, contentDescription = it.message,
                    modifier = Modifier
                        .padding(start = 8.sdp)
                        .align(alignment = Alignment.CenterVertically),
                    tint = MaterialTheme.colorPalette.white
                )

                Text(
                    text = it.message,
                    style = MaterialTheme.typography.smallUbuntuWhiteBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(end = 8.sdp, start = 8.sdp)
                        .align(alignment = Alignment.CenterVertically)
                )
            }
        }
    }
}