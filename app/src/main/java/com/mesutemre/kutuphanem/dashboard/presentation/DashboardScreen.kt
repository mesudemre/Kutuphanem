package com.mesutemre.kutuphanem.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.insets.systemBarsPadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mesutemre.kutuphanem.dashboard.presentation.DashboardViewModel
import com.mesutemre.kutuphanem.dashboard.presentation.components.DashBoardSearchBar
import com.mesutemre.kutuphanem.dashboard.presentation.components.IntroductionPagerArea
import com.mesutemre.kutuphanem.dashboard.presentation.components.PersonelInfoArea
import com.mesutemre.kutuphanem.dashboard.presentation.components.category.DashboardCategoryArea
import com.mesutemre.kutuphanem.navigation.KutuphanemNavigationItem
import com.mesutemre.kutuphanem.ui.theme.colorPalette

@Composable
fun DashboardScreen(
    navController: NavController,
    dashboardViewModel: DashboardViewModel = hiltViewModel()) {
    val dashBoardState = dashboardViewModel.dashboardState.value
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = MaterialTheme.colorPalette.lacivert
    )
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorPalette.loginBackColor)
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        DashBoardSearchBar(notificationCount = 9,
            onClickSearch = {
                navController.navigate(KutuphanemNavigationItem.DashboardSearchScreen.screenRoute)
            }) {

        }
        PersonelInfoArea(dashBoardState.kullaniciBilgi)
        IntroductionPagerArea(dashBoardState.introductionList)
        DashboardCategoryArea(dashBoardState.kategoriListeResource)
    }
}