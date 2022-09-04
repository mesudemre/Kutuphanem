package com.mesutemre.kutuphanem.dashboard.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.mesutemre.kutuphanem.dashboard.domain.model.IntroductionPagerData
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.mediumAllegraBlackBold
import com.mesutemre.kutuphanem.ui.theme.sdp

@OptIn(ExperimentalPagerApi::class)
@Composable
fun IntroductionPagerArea(pagerList: List<IntroductionPagerData>) {
    val pagerState = rememberPagerState()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.sdp)
            .offset(y = -24.sdp)
            .height(80.sdp),
        backgroundColor = MaterialTheme.colorPalette.white,
        shape = MaterialTheme.shapes.medium,
        elevation = 8.sdp
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            HorizontalPager(
                count = pagerList.size, state = pagerState, modifier = Modifier
                    .fillMaxWidth()
            ) { page ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.sdp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(id = pagerList[page].description),
                        style = MaterialTheme.typography.mediumAllegraBlackBold,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.sdp))
            IntroductionDotsIndicator(
                totalDot = pagerList.size,
                selectedIndex = pagerState.currentPage
            )
        }
    }
}