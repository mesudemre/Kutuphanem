package com.mesutemre.kutuphanem.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.mesutemre.kutuphanem.MainScreen
import com.mesutemre.kutuphanem.login.presentation.LoginScreen
import com.mesutemre.kutuphanem.util.navigation.KutuphanemNavigationConst

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun KutuphanemNavigation(navController:NavHostController,
                         startDestinition:String,
                         showSnackbar: (String, SnackbarDuration, Int) -> Unit) {
    //TODO : Bu kısımda sealed class kullanılabilir.

    /*
        composable(
            route = KutuphanemNavigationConst.LOGIN2_SCREEN,
            enterTransition = {
                slideInVertically(tween(1000))
            }
        ){
            Login2Screen()
        }*/

    AnimatedNavHost(navController = navController, startDestination = startDestinition ) {
        composable(route = KutuphanemNavigationConst.MAIN_SCREEN) {
            MainScreen(navController = navController)
        }

        composable(route = KutuphanemNavigationConst.LOGIN_SCREEN){
            LoginScreen(showSnackbar = showSnackbar)
        }
    }
}