package com.mesutemre.kutuphanem.dashboard.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.sdp

@Composable
fun IntroductionDotsIndicator(
    totalDot: Int,
    selectedIndex: Int,
    selectedColor: Color = MaterialTheme.colorPalette.lacivert,
    unSelectedColor: Color = MaterialTheme.colorPalette.lacivert.copy(
        alpha = 0.4f
    )
) {
    Box(modifier = Modifier.fillMaxWidth().padding(top = 2.sdp),contentAlignment = Alignment.Center) {
        LazyRow(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
        ) {
            items(totalDot) {index->
                if (index == selectedIndex) {
                    Box(
                        modifier = Modifier
                            .size(height = 8.sdp, width = 12.sdp)
                            .clip(MaterialTheme.shapes.medium)
                            .background(selectedColor)
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(8.sdp)
                            .clip(CircleShape)
                            .background(unSelectedColor)
                    )
                }

                if (index != totalDot - 1) {
                    Spacer(modifier = Modifier.padding(horizontal = 2.sdp))
                }
            }
        }
    }

}