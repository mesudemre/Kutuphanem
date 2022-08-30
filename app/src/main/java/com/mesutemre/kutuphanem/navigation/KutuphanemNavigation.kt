package com.mesutemre.kutuphanem.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.material.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.mesutemre.kutuphanem.dashboard.DashboardScreen
import com.mesutemre.kutuphanem.dashboard_search.presentation.DashboardSearchScreen
import com.mesutemre.kutuphanem.login.presentation.LoginScreen
import com.mesutemre.kutuphanem.parameter.ParametreScreen
import com.mesutemre.kutuphanem.parameter.ekleme.presentation.ParametreEklemeScreen
import com.mesutemre.kutuphanem.parameter.kitaptur.presentation.ParametreKitapTurScreen
import com.mesutemre.kutuphanem.parameter.yayinevi.presentation.ParametreYayinEviScreen

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
        composable(route = KutuphanemNavigationItem.DashboardScreen.screenRoute) {
            DashboardScreen(navController)
        }

        composable(route = KutuphanemNavigationItem.LoginScreen.screenRoute) {
            LoginScreen(showSnackbar = showSnackbar, navController = navController)
        }

        composable(route = KutuphanemNavigationItem.ParameterScreen.screenRoute,
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(200))
            },
        exitTransition = {
            slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(200))
        }) {
            ParametreScreen(navController)
        }

        composable(route = KutuphanemNavigationItem.ParameterYayinEviScreen.screenRoute,
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(200))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(200))
            }) {
            ParametreYayinEviScreen(showSnackbar = showSnackbar)
        }

        composable(route = KutuphanemNavigationItem.ParameterKitapTurScreen.screenRoute,
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(200))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(200))
            }) {
            ParametreKitapTurScreen(showSnackbar = showSnackbar)
        }

        composable(route = KutuphanemNavigationItem.ParameterEklemeScreen.screenRoute,
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(200))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(200))
            }) {
            ParametreEklemeScreen(showSnackbar = showSnackbar)
        }

        composable(route = KutuphanemNavigationItem.DashboardSearchScreen.screenRoute,
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(200))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(200))
            }) {
            DashboardSearchScreen(navController)
        }
    }
}

@Composable
fun NavHostController.isBottomNavigationTopBarVisible(isBottomNavigation: Boolean = false): Boolean {
    val navBackStackEntry by this.currentBackStackEntryAsState()
    val route = navBackStackEntry?.destination?.route
    val list = KutuphanemNavigationItem::class.nestedClasses.map {
        it.objectInstance as KutuphanemNavigationItem
    }
    val pageItem = list.filter {
        it.screenRoute == route
    }
    if (isBottomNavigation)
        return route != null && pageItem.size>0 && pageItem[0].showBottomBar
    return route != null && pageItem.size>0 && pageItem[0].showTopBar
}

@Composable
fun NavHostController.getCurrentNavigationItem(): KutuphanemNavigationItem? {
    val navBackStackEntry by this.currentBackStackEntryAsState()
    val route = navBackStackEntry?.destination?.route
    val list = KutuphanemNavigationItem::class.nestedClasses.map {
        it.objectInstance as KutuphanemNavigationItem
    }
    val pageItem = list.filter {
        it.screenRoute == route
    }
    if (pageItem != null && pageItem.size>0)
        return pageItem[0]
    return null
}

fun NavOptionsBuilder.popUpToTop(navController: NavController) {
    popUpTo(navController.currentBackStackEntry?.destination?.route ?: return) {
        inclusive =  true
    }
}