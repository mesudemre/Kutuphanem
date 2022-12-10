package com.mesutemre.kutuphanem.kitap_detay.presentation.components.bottomsheet

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem_ui.theme.normalUbuntuTransparent

@Composable
fun KitapDetayAciklamaBottomSheet(kitapAciklama: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.92f)
            .padding(horizontal = 8.sdp)
            .verticalScroll(rememberScrollState())
    ) {
        Divider(
            modifier = Modifier
                .padding(vertical = 4.sdp)
                .width(24.sdp)
                .clip(shape = MaterialTheme.shapes.small)
                .align(Alignment.CenterHorizontally),
            thickness = 3.sdp,
            color = MaterialTheme.colorPalette.otherGrayLight
        )
        Text(
            text = kitapAciklama,
            modifier = Modifier.padding(top = 4.sdp, bottom = 4.sdp),
            style = MaterialTheme.typography.normalUbuntuTransparent
        )
    }
}