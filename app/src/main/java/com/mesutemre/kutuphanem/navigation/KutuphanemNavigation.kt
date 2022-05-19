package com.mesutemre.kutuphanem.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.mesutemre.kutuphanem.login.presentation.LoginScreen
import com.mesutemre.kutuphanem.mainscreen.MainScreen

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
            MainScreen()
        }

        composable(route = KutuphanemNavigationItem.LoginScreen.screenRoute) {
            LoginScreen(showSnackbar = showSnackbar)
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