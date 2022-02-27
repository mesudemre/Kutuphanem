package com.mesutemre.kutuphanem.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mesutemre.kutuphanem.MainScreen
import com.mesutemre.kutuphanem.util.navigation.KutuphanemNavigationConst

@Composable
fun KutuphanemNavigation() {
    //TODO : Bu kısımda sealed class kullanılabilir.

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = KutuphanemNavigationConst.MAIN_SCREEN ) {

        composable(KutuphanemNavigationConst.MAIN_SCREEN) {
            MainScreen()
        }
    }
}