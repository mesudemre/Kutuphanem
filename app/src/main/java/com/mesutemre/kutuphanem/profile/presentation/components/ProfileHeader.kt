package com.mesutemre.kutuphanem.profile.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.mesutemre.kutuphanem_ui.theme.colorPalette
import com.mesutemre.kutuphanem_ui.theme.koyuMavi
import com.mesutemre.kutuphanem_ui.theme.mediumUbuntuWhiteBold
import com.mesutemre.kutuphanem_ui.theme.sdp

@Composable
fun ProfileHeader(
    onBackPress: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(140.sdp)
            .fillMaxWidth()
            .background(Brush.horizontalGradient(listOf(koyuMavi, Color(0XFF02BCF8))))
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.sdp, vertical = 32.sdp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                modifier = Modifier
                    .size(36.sdp)
                    .background(
                        color = MaterialTheme.colorPalette.loginBackColor.copy(
                            alpha = 0.32f
                        ),
                        shape = CircleShape
                    ),
                onClick = onBackPress
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    tint = MaterialTheme.colorPalette.white,
                    contentDescription = null
                )
            }

            Text(
                text = stringResource(id = com.mesutemre.kutuphanem.R.string.profilimItem),
                modifier = Modifier.padding(start = 16.sdp),
                style = MaterialTheme.typography.mediumUbuntuWhiteBold
            )
        }
    }
}