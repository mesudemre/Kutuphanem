package com.mesutemre.kutuphanem.kitap_ekleme.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import com.mesutemre.kutuphanem.ui.theme.mediumUbuntuWhite
import com.mesutemre.kutuphanem_ui.extensions.rippleClick
import com.mesutemre.kutuphanem_ui.theme.colorPalette
import com.mesutemre.kutuphanem_ui.theme.sdp

@Composable
fun KitapEklemeScreenTopBar(
    pageTitle: String,
    onBackPress: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.sdp)
            .background(color = MaterialTheme.colorPalette.lacivert)
    ) {
        val (titleText, backIcon) = createRefs()
        Icon(
            Icons.Filled.ArrowBack, modifier = Modifier
                .constrainAs(backIcon) {
                    top.linkTo(parent.top, 40.sdp)
                    bottom.linkTo(parent.bottom, 20.sdp)
                }
                .padding(horizontal = 8.sdp)
                .rippleClick {
                    onBackPress()
                }, contentDescription = pageTitle, tint = MaterialTheme.colorPalette.white
        )
        Text(text = pageTitle,
            modifier = Modifier
                .constrainAs(titleText) {
                    top.linkTo(parent.top, 40.sdp)
                    bottom.linkTo(parent.bottom, 20.sdp)
                    centerVerticallyTo(backIcon)
                    centerHorizontallyTo(parent)
                }
                .padding(horizontal = 8.sdp),
            style = MaterialTheme.typography.mediumUbuntuWhite)
    }
}