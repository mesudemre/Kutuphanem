package com.mesutemre.kutuphanem_ui.extensions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.mesutemre.kutuphanem_ui.theme.colorPalette
import com.mesutemre.kutuphanem_ui.theme.sdp

@Composable
fun Modifier.rippleClick(
    radius: Dp = 16.sdp,
    color: Color = Color.Transparent,
    onClick: () -> Unit
): Modifier {
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

@Composable
fun Modifier.shimmerEffect(): Modifier {
    return this.placeholder(
        visible = true,
        color = MaterialTheme.colorPalette.placeHolderColor,
        highlight = PlaceholderHighlight.shimmer(highlightColor = MaterialTheme.colorPalette.otherGrayLight)
    )
}

@Composable
fun Modifier.gesturesDisabled(disabled: Boolean = true) =
    if (disabled) {
        pointerInput(Unit) {
            awaitPointerEventScope {
                while (true) {
                    awaitPointerEvent(pass = PointerEventPass.Initial)
                        .changes
                        .forEach(PointerInputChange::consume)
                }
            }
        }
    } else {
        this
    }