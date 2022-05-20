package com.mesutemre.kutuphanem.parameter.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mesutemre.kutuphanem.ui.theme.*

@Composable
fun ParametreRowItem(detail: String) {
    Column(modifier = Modifier.fillMaxWidth().height(40.sdp)) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = detail, modifier = Modifier
                    .padding(end = 4.sdp)
                    .weight(0.9f),
                style = MaterialTheme.typography.normalUbuntuTransparent
            )

            Icon(
                Icons.Filled.Delete,
                modifier = Modifier
                    .padding(horizontal = 2.sdp)
                    .weight(0.1f)
                    .align(alignment = Alignment.CenterVertically),
                contentDescription = detail,
                tint = MaterialTheme.colorPalette.transparent
            )
        }
        Divider(
            modifier = Modifier.padding(top = 4.sdp),
            thickness = 1.sdp, color = MaterialTheme.colorPalette.otherGrayLight
        )
    }
}