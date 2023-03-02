package com.mesutemre.kutuphanem.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.insets.systemBarsPadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.dashboard.presentation.DashboardViewModel
import com.mesutemre.kutuphanem.dashboard.presentation.components.DashBoardSearchBar
import com.mesutemre.kutuphanem.dashboard.presentation.components.IntroductionPagerArea
import com.mesutemre.kutuphanem.dashboard.presentation.components.PersonelInfoArea
import com.mesutemre.kutuphanem.dashboard.presentation.components.category.DashboardCategoryArea
import com.mesutemre.kutuphanem.dashboard.presentation.components.statistics.kitaptur.StatisticsArea
import com.mesutemre.kutuphanem.dashboard.presentation.components.statistics.StatisticsDescriptionArea
import com.mesutemre.kutuphanem.dashboard.presentation.components.statistics.kitapyil.KitapYilStatisticsArea
import com.mesutemre.kutuphanem.navigation.KutuphanemNavigationItem
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.sdp

@Composable
fun DashboardScreen(
    navController: NavController,
    dashboardViewModel: DashboardViewModel = hiltViewModel()
) {
    val dashBoardState = dashboardViewModel.dashboardState.collectAsState().value
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
                dashboardViewModel.triggerEvent()
                navController.navigate(KutuphanemNavigationItem.DashboardSearchScreen.screenRoute)
            }) {

        }
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            PersonelInfoArea(dashBoardState.kullaniciBilgi)
            IntroductionPagerArea(dashBoardState.introductionList)
            DashboardCategoryArea(dashBoardState.kategoriListeResource)
            StatisticsArea(
                dashBoardState.kitapTurIstatistikResource,
                dashBoardState.kitapTurIstatistikList
            )
            StatisticsDescriptionArea(
                description = stringResource(id = R.string.dasboard_category_statistics_info),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.sdp)
            )
            KitapYilStatisticsArea(
                dashBoardState.kitapYilIstatistikResource,
                dashBoardState.kitapYilIstatistikList
            )
        }
    }
}