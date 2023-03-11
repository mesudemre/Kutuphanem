package com.mesutemre.kutuphanem.profile.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem_ui.theme.colorPalette
import com.mesutemre.kutuphanem_ui.theme.sdp
import com.mesutemre.kutuphanem_ui.theme.smallUbuntuTransparentBold

@Composable
fun ProfileItemCard(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.sdp)
            .clickable {
                onClick()
            },
        elevation = 2.sdp,
        backgroundColor = MaterialTheme.colorPalette.white,
        border = BorderStroke(
            (1 / 2).sdp,
            color = MaterialTheme.colorPalette.transparent
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.sdp, horizontal = 4.sdp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(24.sdp)
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.smallUbuntuTransparentBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 8.sdp)
                )
            }
            Icon(
                Icons.Filled.ChevronRight,
                modifier = Modifier.size(24.sdp),
                contentDescription = title,
                tint = MaterialTheme.colorPalette.transparent
            )
        }
    }
}