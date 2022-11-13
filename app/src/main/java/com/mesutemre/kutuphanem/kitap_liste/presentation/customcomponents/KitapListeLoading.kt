package com.mesutemre.kutuphanem.kitap_liste.presentation.customcomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem_ui.extensions.shimmerEffect
import com.mesutemre.kutuphanem_ui.theme.sdp

@Composable
fun KitapListeLoading() {
    LazyColumn {
        items(3) {
            Row(
                modifier = Modifier
                    .padding(
                        top = 8.sdp,
                        start = 8.sdp,
                        end = 8.sdp,
                        bottom = 16.sdp
                    )
                    .background(color = MaterialTheme.colorPalette.white)
                    .clip(shape = MaterialTheme.shapes.medium)
            ) {
                Spacer(
                    modifier = Modifier
                        .width(100.sdp)
                        .height(150.sdp)
                        .border(
                            1.sdp,
                            MaterialTheme.colorPalette.secondaryGray,
                            MaterialTheme.shapes.medium
                        )
                        .shimmerEffect()
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.sdp)
                ) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(16.sdp)
                            .padding(top = 4.sdp, end = 8.sdp)
                            .shimmerEffect()
                    )
                    Spacer(
                        modifier = Modifier
                            .width(80.sdp)
                            .height(16.sdp)
                            .padding(top = 4.sdp, end = 8.sdp)
                            .shimmerEffect()
                    )
                    Spacer(
                        modifier = Modifier
                            .width(120.sdp)
                            .height(16.sdp)
                            .padding(top = 4.sdp)
                            .shimmerEffect()
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.sdp)
                            .padding(top = 12.sdp, end = 8.sdp)
                            .shimmerEffect()
                    )

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.sdp)
                            .padding(top = 4.sdp, end = 8.sdp)
                            .shimmerEffect()
                    )

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.sdp)
                            .padding(top = 4.sdp, end = 8.sdp)
                            .shimmerEffect()
                    )
                }
            }
        }
    }
}

