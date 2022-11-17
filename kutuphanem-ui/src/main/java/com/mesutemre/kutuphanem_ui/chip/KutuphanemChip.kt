package com.mesutemre.kutuphanem_ui.chip

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import com.mesutemre.kutuphanem_ui.theme.colorPalette
import com.mesutemre.kutuphanem_ui.theme.sdp
import com.mesutemre.kutuphanem_ui.theme.smallUbuntuBlack
import com.mesutemre.kutuphanem_ui.theme.smallUbuntuBlackBold

@Composable
fun KutuphanemBaseChip(
    modifier: Modifier,
    title: String,
    leadingIcon: ImageVector? = null,
    leadingIconModifier:Modifier = Modifier,
    disableIconTint:Color = MaterialTheme.colorPalette.secondaryGray,
    selectedIconTint: Color = MaterialTheme.colorPalette.black,
    unSelectedIconTint: Color = MaterialTheme.colorPalette.transparent,
    isSelected: Boolean = false,
    isDisable: Boolean = false,
    selectedTextStyle: TextStyle = MaterialTheme.typography.smallUbuntuBlackBold,
    unselectedTextStyle: TextStyle = MaterialTheme.typography.smallUbuntuBlack,
    selectedTextColor: Color = MaterialTheme.colorPalette.black,
    unSelectedTextColor: Color = MaterialTheme.colorPalette.black,
    disableBorderColor: Color = MaterialTheme.colorPalette.transparent,
    selectedBorderColor: Color = MaterialTheme.colorPalette.secondaryGray,
    unSelectedBorderColor: Color = MaterialTheme.colorPalette.secondaryGray,
    borderSize: Int = 1,
    selectedBackgroundColor: Color = MaterialTheme.colorPalette.acikTuruncu,
    unSelectedBackgroundColor: Color = MaterialTheme.colorPalette.white,
    disabledBackgroundColor: Color = MaterialTheme.colorPalette.transparent,
    selectedElevation: Int = 8,
    unSelectedElevation:Int = 4,
    onSelect: (Boolean) -> Unit
) {
    Card(
        modifier = modifier
            .wrapContentWidth()
            .clip(MaterialTheme.shapes.medium)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = false),
                onClick = {
                    onSelect(!isSelected)
                }
            ),
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(width = borderSize.sdp, color = if (isDisable) disableBorderColor else {
            if (isSelected) selectedBorderColor else unSelectedBorderColor
        }),
        backgroundColor = if (isDisable) disabledBackgroundColor else {
            if (isSelected) selectedBackgroundColor else unSelectedBackgroundColor
        },
        elevation = if (isDisable || !isSelected) unSelectedElevation.sdp else selectedElevation.sdp
    ) {
        Row(modifier = Modifier.padding(start = 2.sdp, end = 2.sdp),verticalAlignment = Alignment.CenterVertically) {
            leadingIcon?.let {
                Icon(imageVector = it,
                    modifier = leadingIconModifier,
                    tint = if (isDisable) disableIconTint else {
                        if (isSelected) selectedIconTint else unSelectedIconTint
                    },
                    contentDescription = title)
            }
            Text(
                text = title,
                style = if (isDisable) unselectedTextStyle else {
                    if (isSelected) selectedTextStyle else unselectedTextStyle
                },
                color = if (isDisable) unSelectedTextColor else {
                    if (isSelected) selectedTextColor else unSelectedTextColor
                }
            )
        }
    }
}


