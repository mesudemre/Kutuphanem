package com.mesutemre.kutuphanem.kitap_detay.presentation.components.header

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem_ui.extensions.rippleClick

@Composable
fun KitapDetayHeaderBar(
    /*onClickBack:()->Unit,
    onClickShare:()->Unit,
    onClickArchive:()->Unit*/
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = null,
            tint = MaterialTheme.colorPalette.white,
            modifier = Modifier
                .size(24.sdp)
                .rippleClick { })
        Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.End) {
            Icon(
                imageVector = Icons.Filled.Share,
                contentDescription = null,
                modifier = Modifier
                    .size(24.sdp)
                    .rippleClick { },
                tint = MaterialTheme.colorPalette.white
            )
            Icon(
                imageVector = Icons.Filled.Archive,
                contentDescription = null,
                tint = MaterialTheme.colorPalette.white,
                modifier = Modifier
                    .padding(start = 16.sdp)
                    .size(24.sdp)
                    .rippleClick { }
            )
        }
    }
}