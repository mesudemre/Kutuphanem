package com.mesutemre.kutuphanem.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mesutemre.kutuphanem.MainScreen
import com.mesutemre.kutuphanem.login.presentation.LoginScreen
import com.mesutemre.kutuphanem.util.navigation.KutuphanemNavigationConst

@Composable
fun KutuphanemNavigation(startDestinition:String) {
    //TODO : Bu kısımda sealed class kullanılabilir.

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestinition ) {

        composable(KutuphanemNavigationConst.MAIN_SCREEN) {
            MainScreen(navController = navController)
        }

        composable(KutuphanemNavigationConst.LOGIN_SCREEN) {
            LoginScreen()
        }
    }
}