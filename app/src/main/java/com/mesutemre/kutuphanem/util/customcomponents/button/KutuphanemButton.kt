package com.mesutemre.kutuphanem.util.customcomponents.button

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.mesutemre.kutuphanem.ui.theme.*

enum class ButtonSize(val buttonSize: Dp) {
    LARGE(42.sdp),
    MEDIUM(32.sdp),
    SMALL(22.sdp)
}

@Composable
fun KutuphanemBaseButton(
    modifier: Modifier = Modifier,
    buttonSize: ButtonSize,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.mediumUbuntuWhite,
    borderColor: Brush = SolidColor(Color.Transparent),
    textColor: Color,
    isEnabled: Boolean = true,
    iconModifier: Modifier? = null,
    iconId: Int? = null,
    iconColor: Color = Color.Unspecified,
    backgroundBrush: Brush? = null,
    onClick: () -> Unit,
) {
    Button(
        enabled = isEnabled,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
            contentColor = textColor,
            disabledBackgroundColor = Color.Transparent,
            disabledContentColor = textColor.copy(alpha = 0.5f)
        ),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.sdp,
            pressedElevation = 0.sdp
        ),
        contentPadding = PaddingValues(),
        modifier = modifier
            .height(height = buttonSize.buttonSize)
            .clip(RoundedCornerShape(6.sdp))
            .width(intrinsicSize = IntrinsicSize.Max)
            .alpha(if (isEnabled) 1f else 0.5f),
        border = if (borderColor != SolidColor(Color.Transparent)) BorderStroke(
            1.sdp,
            borderColor
        ) else null
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundBrush ?: SolidColor(Color.Transparent))
                .padding(start = if (iconModifier == null) 12.sdp else 16.sdp, end = 12.sdp)
        ) {
            if (iconId != null) {
                Icon(
                    painter = painterResource(id = iconId),
                    contentDescription = "",
                    modifier = iconModifier?.align(Alignment.CenterStart) ?: Modifier.align(
                        Alignment.CenterStart
                    ),
                    tint = iconColor
                )
            }
            Text(
                text = text,
                modifier = Modifier
                    .align(Alignment.Center),
                style = textStyle,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
fun KutuphanemMainMaterialButton(
    modifier: Modifier,
    text: String,
    textStyle:TextStyle = MaterialTheme.typography.mediumUbuntuWhite,
    textColor: Color = MaterialTheme.colorPalette.white,
    @DrawableRes iconId: Int? = null,
    iconColor: Color = MaterialTheme.colorPalette.white,
    borderColor:Color = MaterialTheme.colorPalette.primaryTextColor,
    isEnabled: Boolean = true,
    backgroundColor:Color = MaterialTheme.colorPalette.primaryTextColor,
    onClick: () -> Unit,
) {
    Button(onClick = onClick, enabled = isEnabled,
        shape = Shapes.medium,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
            contentColor = textColor,
            disabledBackgroundColor = Color.Transparent,
            disabledContentColor = textColor.copy(alpha = 0.5f)
        ),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.sdp,
            pressedElevation = 0.sdp
        ),
        contentPadding = PaddingValues(),
        modifier = modifier
            .height(height = ButtonSize.MEDIUM.buttonSize)
            .clip(RoundedCornerShape(6.sdp))
            .width(intrinsicSize = IntrinsicSize.Max)
            .alpha(if (isEnabled) 1f else 0.5f),
        border = BorderStroke(1.sdp,borderColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(start = 12.sdp , end = 12.sdp)
        ) {
            if (iconId != null) {
                Icon(
                    painter = painterResource(id = iconId),
                    contentDescription = "",
                    modifier = Modifier.align(
                        Alignment.CenterStart
                    ),
                    tint = iconColor
                )
            }
            Text(
                text = text,
                modifier = Modifier
                    .align(Alignment.Center),
                style = textStyle,
                color = textColor,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
fun KutuphanemFormButton(
    text: String,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    icon: Int? = null,
    iconColor: Color = Color.Unspecified,
    textStyle: TextStyle = MaterialTheme.typography.mediumUbuntuWhite,
    onClick: () -> Unit
) {
    KutuphanemBaseButton(
        text = text,
        buttonSize = ButtonSize.MEDIUM,
        backgroundBrush = buttonGradientBrush,
        iconId = icon,
        iconColor = iconColor,
        textColor = MaterialTheme.colorPalette.white,
        modifier = modifier,
        isEnabled = isEnabled,
        textStyle = textStyle,
        onClick = onClick
    )
}
