package com.mesutemre.kutuphanem.profile.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mesutemre.kutuphanem_ui.theme.normalUbuntuTransparentBold
import com.mesutemre.kutuphanem_ui.theme.sdp

@Composable
fun ProfileItemTitle(
    title: String
) {
    Text(
        text = title,
        modifier = Modifier.padding(start = 12.sdp, top = 16.sdp),
        style = MaterialTheme.typography.normalUbuntuTransparentBold
    )
}