package com.mesutemre.kutuphanem.profile.presentation.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.*
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.mesutemre.kutuphanem_ui.extensions.rippleClick
import com.mesutemre.kutuphanem_ui.theme.*

@OptIn(ExperimentalMotionApi::class)
@Composable
fun ProfileHeader(
    adSoyad: String,
    kullaniciResim: String,
    lazyScrollState: LazyListState,
    onBackPress: () -> Unit
) {
    val context = LocalContext.current
    val motionScene = remember {
        context.resources.openRawResource(com.mesutemre.kutuphanem.R.raw.profile_motion_scene)
            .readBytes().decodeToString()
    }

    val proggress by animateFloatAsState(
        targetValue = if (lazyScrollState.firstVisibleItemIndex in 0..2) 0f else 1f,
        tween(500)
    )
    val motionHeight by animateDpAsState(
        targetValue = if (lazyScrollState.firstVisibleItemIndex in 0..2) 320.sdp else 120.sdp,
        tween(500)
    )
    MotionLayout(
        motionScene = MotionScene(content = motionScene),
        progress = proggress,
        modifier = Modifier
            .fillMaxWidth()
            .height(motionHeight)
    ) {
        val roundedShape = RoundedCornerShape(
            bottomStart = 24.sdp,
            bottomEnd = 24.sdp
        )
        Box(
            modifier = Modifier
                .layoutId("header_box")
                .clip(roundedShape)
                .background(Brush.horizontalGradient(listOf(koyuMavi, Color(0XFF02BCF8))))
        )
        Row(
            modifier = Modifier
                .layoutId("header_box_row"),
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

        Box(
            modifier = Modifier
                .layoutId("header_box_userimage")
        ) {
            SubcomposeAsyncImage(model = kullaniciResim,
                modifier = Modifier.clip(
                    shape = RoundedCornerShape(
                        topStart = 8.sdp,
                        topEnd = 8.sdp,
                        bottomStart = 8.sdp,
                        bottomEnd = 8.sdp
                    )
                ),
                contentDescription = adSoyad,
                loading = {
                    if (painter.state is AsyncImagePainter.State.Loading || painter.state is AsyncImagePainter.State.Error) {
                        //TODO : Burada shimmer gösterilebilir...
                    } else {
                        SubcomposeAsyncImageContent()
                    }
                })
            Icon(
                Icons.Filled.Settings,
                contentDescription = null,
                modifier = Modifier
                    .size(24.sdp)
                    .offset(x = (104).sdp, y = (-8).sdp)
                    .rippleClick {
                    },
                tint = MaterialTheme.colorPalette.turuncu
            )
        }

        Icon(
            Icons.Filled.Logout,
            contentDescription = null,
            modifier = Modifier
                .size(24.sdp)
                .layoutId("header_box_logout_icon")
                .rippleClick {
                },
            tint = MaterialTheme.colorPalette.white
        )

        Text(
            text = adSoyad,
            modifier = Modifier.layoutId("header_box_username"),
            style = MaterialTheme.typography.mediumUbuntuBlackBold
        )

    }
}
