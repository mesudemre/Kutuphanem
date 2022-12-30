package com.mesutemre.kutuphanem.kitap_detay.presentation.components.comments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem_ui.theme.colorPalette
import com.mesutemre.kutuphanem_ui.theme.sdp
import com.mesutemre.kutuphanem_ui.theme.smallUbuntuBlack
import com.mesutemre.kutuphanem_ui.theme.ssp

@Composable
fun KitapYorumYasalUyariItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorPalette.acikGri)
            .height(64.sdp), contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.sdp),
            text = stringResource(id = R.string.kitapYorumSaygiLabel),
            style = MaterialTheme.typography.smallUbuntuBlack.copy(
                lineHeight = 14.ssp
            )
        )
    }
}