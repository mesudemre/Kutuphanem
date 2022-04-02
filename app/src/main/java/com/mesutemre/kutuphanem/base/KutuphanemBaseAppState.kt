package com.mesutemre.kutuphanem.base

import androidx.compose.compiler.plugins.kotlin.ComposeFqNames.remember
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * @Author: mesutemre.celenk
 * @Date: 2.04.2022
 */
class KutuphanemBaseAppState(
    val scaffoldState: ScaffoldState,
    val coroutineScope: CoroutineScope
) {

}

@Composable
fun rememberKutuphanemAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(
        snackbarHostState = remember {
            SnackbarHostState()
        }
    ),
    snackbarScope: CoroutineScope = rememberCoroutineScope()
) = remember(scaffoldState, snackbarScope) {
    KutuphanemBaseAppState(scaffoldState = scaffoldState, coroutineScope = snackbarScope)
}