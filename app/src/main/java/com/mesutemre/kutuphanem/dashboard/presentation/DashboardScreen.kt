package com.mesutemre.kutuphanem.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.systemBarsPadding
import com.mesutemre.kutuphanem.dashboard.presentation.DashboardViewModel
import com.mesutemre.kutuphanem.dashboard.presentation.components.DashBoardSearchBar
import com.mesutemre.kutuphanem.dashboard.presentation.components.IntroductionPagerArea
import com.mesutemre.kutuphanem.dashboard.presentation.components.PersonelInfoArea
import com.mesutemre.kutuphanem.ui.theme.colorPalette

@Composable
fun DashboardScreen(dashboardViewModel: DashboardViewModel = hiltViewModel()) {
    val dashBoardState = dashboardViewModel.dashboardState.value
    Column(modifier = Modifier
        .background(color = MaterialTheme.colorPalette.loginBackColor)
        .fillMaxSize()
        .systemBarsPadding()) {

        DashBoardSearchBar(searchInput = "", notificationCount = 9,{}) {
        }
        PersonelInfoArea(dashBoardState.kullaniciBilgi)
        IntroductionPagerArea(dashBoardState.introductionList)
    }
}