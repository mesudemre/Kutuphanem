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
import com.mesutemre.kutuphanem.model.ERROR
import com.mesutemre.kutuphanem.model.SUCCESS
import com.mesutemre.kutuphanem.model.WARNING
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem.ui.theme.smallUbuntuWhiteBold

@Composable
fun KutuphanemSnackBarHost(
    snackbarHostState: SnackbarHostState,
    type: Int,
) {
        SnackbarHost(hostState = snackbarHostState
        ) {
            Card(
                shape = MaterialTheme.shapes.medium,
                backgroundColor = when (type) {
                    SUCCESS ->
                        MaterialTheme.colorPalette.fistikYesil
                    WARNING ->
                        MaterialTheme.colorPalette.acikTuruncu
                    ERROR ->
                        MaterialTheme.colorPalette.kirmizi
                    else -> MaterialTheme.colorPalette.fistikYesil
                },
                modifier = Modifier
                    .padding(start = 24.sdp,end = 24.sdp,bottom = 24.sdp)
                    .height(50.sdp).fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    when (type) {
                        SUCCESS ->
                            Icon(
                                Icons.Filled.Check, contentDescription = it.message,
                                modifier = Modifier
                                    .padding(start = 8.sdp)
                                    .align(alignment = Alignment.CenterVertically),
                                tint = MaterialTheme.colorPalette.white
                            )
                        WARNING ->
                            Icon(
                                Icons.Filled.Warning, contentDescription = it.message,
                                modifier = Modifier
                                    .padding(start = 8.sdp)
                                    .align(alignment = Alignment.CenterVertically),
                                tint = MaterialTheme.colorPalette.white
                            )
                        ERROR ->
                            Icon(
                                Icons.Filled.Error, contentDescription = it.message,
                                modifier = Modifier
                                    .padding(start = 8.sdp)
                                    .align(alignment = Alignment.CenterVertically),
                                tint = MaterialTheme.colorPalette.white
                            )
                    }

                    Text(
                        text = it.message,
                        style = MaterialTheme.typography.smallUbuntuWhiteBold,
                        modifier = Modifier
                            .padding(end = 8.sdp,start = 8.sdp)
                            .align(alignment = Alignment.CenterVertically)
                    )
                }
            }
        }


}