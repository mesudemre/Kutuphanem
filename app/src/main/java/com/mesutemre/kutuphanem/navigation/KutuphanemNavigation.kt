package com.mesutemre.kutuphanem.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.mesutemre.kutuphanem.MainScreen
import com.mesutemre.kutuphanem.login.presentation.LoginScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun KutuphanemNavigation(
    navController: NavHostController,
    startDestinition: KutuphanemNavigationItem,
    showSnackbar: (String, SnackbarDuration, Int) -> Unit
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = startDestinition.screenRoute
    ) {
        composable(route = KutuphanemNavigationItem.MainScreen.screenRoute) {
            MainScreen(navController = navController)
        }

        composable(route = KutuphanemNavigationItem.LoginScreen.screenRoute) {
            LoginScreen(showSnackbar = showSnackbar)
        }
    }
}