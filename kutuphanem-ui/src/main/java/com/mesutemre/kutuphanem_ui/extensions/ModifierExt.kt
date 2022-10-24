package com.mesutemre.kutuphanem_ui.extensions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.mesutemre.kutuphanem_ui.theme.sdp

@Composable
fun Modifier.rippleClick(
    radius:Dp = 16.sdp,
    color: Color = Color.Transparent,
    onClick:()->Unit
):Modifier {
    return clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = rememberRipple(
            bounded = false,
            radius = radius,
            color = color
        )
    ) {
        onClick()
    }
}