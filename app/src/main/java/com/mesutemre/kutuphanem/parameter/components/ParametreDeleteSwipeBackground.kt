package com.mesutemre.kutuphanem.parameter.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.sdp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ParametreDeleteSwipeBackground(
    dismissState: DismissState,
    iconContentDescription: String? = null,
    onSwipeFinish: () -> Unit
) {
    val haptic = LocalHapticFeedback.current
    val direction = dismissState.dismissDirection
    val scale by animateFloatAsState(
        if (dismissState.targetValue == DismissValue.Default) 0.0f else 1f
    )
    val color by animateColorAsState(
        when (dismissState.targetValue) {
            DismissValue.DismissedToStart -> MaterialTheme.colorPalette.acik_kirmizi
            else -> {
                MaterialTheme.colorPalette.loginBackColor
            }
        }
    )
    if (direction == DismissDirection.EndToStart) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = color),
            contentAlignment = Alignment.CenterEnd
        ) {
            if (color == MaterialTheme.colorPalette.acik_kirmizi) {
                LaunchedEffect(Unit) {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                }
            }
            Icon(
                Icons.Filled.Delete,
                modifier = Modifier
                    .padding(horizontal = 2.sdp)
                    .scale(scale),
                contentDescription = iconContentDescription,
                tint = MaterialTheme.colorPalette.white
            )
        }
    }
    if (dismissState.currentValue != DismissValue.Default) {
        onSwipeFinish.invoke()
        LaunchedEffect(Unit) {
            dismissState.reset()
        }
    }
}