package com.mesutemre.kutuphanem.util.customcomponents.menuitem

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.constraintlayout.compose.ConstraintLayout
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.normalUbuntuBlack
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem.ui.theme.thinyUbuntuTransparent

@Composable
fun KutuphanemMenuItem(
    label: String,
    onItemClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.sdp)
            .padding(horizontal = 16.sdp)
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(
                    color = Color.Black
                ),
                role = Role.Button,
                onClick = onItemClick
            ),
        backgroundColor = MaterialTheme.colorPalette.white,
        shape = MaterialTheme.shapes.medium,
        elevation = 2.sdp
    ) {
        ConstraintLayout {
            val (labelText, iconItem) = createRefs()
            Text(text = label, modifier = Modifier
                .constrainAs(labelText) {
                    top.linkTo(parent.top, 8.sdp)
                    bottom.linkTo(parent.bottom, 8.sdp)
                }
                .padding(horizontal = 8.sdp),
                style = MaterialTheme.typography.normalUbuntuBlack)

            Icon(
                Icons.Filled.ChevronRight,
                modifier = Modifier.constrainAs(iconItem) {
                    top.linkTo(parent.top, 8.sdp)
                    bottom.linkTo(parent.bottom, 8.sdp)
                    end.linkTo(parent.end, 8.sdp)
                },
                contentDescription = label,
                tint = MaterialTheme.colorPalette.lacivert
            )
        }
    }

}

@Composable
fun KutuphanemMenuInfo(info: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.sdp)
            .padding(horizontal = 16.sdp),
        backgroundColor = MaterialTheme.colorPalette.white,
        shape = MaterialTheme.shapes.small,
        elevation = 2.sdp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                Modifier
                    .weight(1f)
            ) {
                Icon(
                    Icons.Filled.Info,
                    modifier = Modifier
                        .padding(horizontal = 2.sdp)
                        .weight(0.1f)
                        .align(alignment = Alignment.CenterVertically),
                    contentDescription = info,
                    tint = MaterialTheme.colorPalette.transparent
                )

                Text(
                    text = info, modifier = Modifier
                        .padding(end = 4.sdp)
                        .weight(0.9f),
                    style = MaterialTheme.typography.thinyUbuntuTransparent
                )
            }
        }
    }

}