package com.mesutemre.kutuphanem.util

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.mesutemre.kutuphanem.util.customcomponents.snackbar.KutuphanemSnackbarState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * @Author: mesutemre.celenk
 * @Date: 1.05.2022
 */
class KutuphanemAppState(
    val kutuphanemSnackbarState: KutuphanemSnackbarState,
    val scaffoldState: ScaffoldState,
    val snackbarScope: CoroutineScope,
    val navController: NavHostController
) {

    fun showSnackbar(
        message: String,
        duration: SnackbarDuration,
        type: Int
    ) {
        snackbarScope.launch {
            kutuphanemSnackbarState.showSnackbar(
                message = message,
                duration = duration,
                type = type
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun rememberKutuphanemAppState(
    snackbarHostState: SnackbarHostState = remember {
        SnackbarHostState()
    },
    kutuphanemSnackbarState: KutuphanemSnackbarState = remember {
        KutuphanemSnackbarState(
            snackbarHostState = snackbarHostState
        )
    },
    scaffoldState: ScaffoldState = rememberScaffoldState(
        snackbarHostState = snackbarHostState
    ),
    navController: NavHostController = rememberAnimatedNavController(),
    snackbarScope: CoroutineScope = rememberCoroutineScope()
) = remember(kutuphanemSnackbarState, scaffoldState, navController, snackbarScope) {
    KutuphanemAppState(
        kutuphanemSnackbarState = kutuphanemSnackbarState,
        scaffoldState = scaffoldState,
        navController = navController,
        snackbarScope = snackbarScope
    )
}