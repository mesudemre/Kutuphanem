package com.mesutemre.kutuphanem.util.customcomponents.menuitem

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.mediumUbuntuBlack
import com.mesutemre.kutuphanem.ui.theme.sdp

@Composable
fun KutuphanemMenuItem(
    label: String,
    onItemClick: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.sdp)
            .padding(horizontal = 16.sdp)
            .clickable {
                onItemClick.invoke()
            },
    ) {
        val (labelText, iconItem) = createRefs()
        Text(text = label, modifier = Modifier
            .constrainAs(labelText) {
                top.linkTo(parent.top, 8.sdp)
                bottom.linkTo(parent.bottom, 8.sdp)
            }
            .padding(horizontal = 8.sdp),
            style = MaterialTheme.typography.mediumUbuntuBlack)

        Icon(
            Icons.Filled.ArrowRight,
            modifier = Modifier.constrainAs(iconItem) {
                top.linkTo(parent.top, 8.sdp)
                bottom.linkTo(parent.bottom, 8.sdp)
                end.linkTo(parent.end, 8.sdp)
            },
            contentDescription = label,
            tint = MaterialTheme.colorPalette.googleDarkGray
        )
    }
}