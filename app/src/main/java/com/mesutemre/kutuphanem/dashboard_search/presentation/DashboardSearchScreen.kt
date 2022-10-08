package com.mesutemre.kutuphanem.dashboard_search.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.insets.systemBarsPadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mesutemre.kutuphanem.dashboard_search.presentation.components.DashboardSearchHistory
import com.mesutemre.kutuphanem.dashboard_search.presentation.components.KitapYazarSearchText
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.mediumUbuntuTransparent
import com.mesutemre.kutuphanem.ui.theme.sdp

@Composable
fun DashboardSearchScreen(
    navController: NavController,
    dashboardSearchViewModel: DashboardSearchScreenViewModel = hiltViewModel()
) {
    val dashBoardSearchState = dashboardSearchViewModel.dashboardSearchState.value
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = MaterialTheme.colorPalette.lacivert
    )
    val localFocusManager = LocalFocusManager.current

    LaunchedEffect(dashBoardSearchState.searchScreenVisibility) {
        if (!dashBoardSearchState.searchScreenVisibility) {
            navController.popBackStack()
        }
    }
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorPalette.loginBackColor)
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        KitapYazarSearchText(
            searchInput = dashBoardSearchState.searchText,
            onBackPressed = {
                localFocusManager.clearFocus()
                dashboardSearchViewModel.onSearchBack()
            }
        ) {
            dashboardSearchViewModel.onSearchKitapYazar(it)
        }

        if (dashBoardSearchState.isSearching) {

        } else {
            DashboardSearchHistory(historyListResource = dashBoardSearchState.historyListResource)
        }
    }
}