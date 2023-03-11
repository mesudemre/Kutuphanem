package com.mesutemre.kutuphanem.profile.presentation.components

import androidx.compose.animation.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.text.style.TextOverflow
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem_ui.extensions.rippleClick
import com.mesutemre.kutuphanem_ui.theme.sdp
import com.mesutemre.kutuphanem_ui.theme.smallUbuntuTransparent

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ProfileAboutArea(
    aboutText: String
) {
    var showAllAbout by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.sdp, horizontal = 24.sdp)
    ) {
        if (showAllAbout.not()) {
            Text(
                text = aboutText,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.smallUbuntuTransparent
            )
        }

        AnimatedVisibility(
            visible = showAllAbout,
            enter = slideInVertically(initialOffsetY = { -40 }) + expandVertically(
                expandFrom = Alignment.Top
            ) + scaleIn(
                transformOrigin = TransformOrigin(0.5f, 0f)
            ) + fadeIn(initialAlpha = 0.3f),
            exit = slideOutVertically() + shrinkVertically() + fadeOut() + scaleOut(targetScale = 1.2f)
        ) {
            Text(
                text = aboutText, style = MaterialTheme.typography.smallUbuntuTransparent
            )
        }

        Icon(imageVector = if (showAllAbout.not()) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
            contentDescription = null,
            tint = MaterialTheme.colorPalette.transparent,
            modifier = Modifier
                .size(32.sdp)
                .align(Alignment.CenterHorizontally)
                .rippleClick {
                    showAllAbout = !showAllAbout
                })
    }
}