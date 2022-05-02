package com.mesutemre.kutuphanem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsPadding
import com.mesutemre.kutuphanem.navigation.KutuphanemNavigation
import com.mesutemre.kutuphanem.ui.theme.KutuphanemTheme
import com.mesutemre.kutuphanem.util.KutuphanemAppState
import com.mesutemre.kutuphanem.util.customcomponents.snackbar.KutuphanemSnackBarHost
import com.mesutemre.kutuphanem.util.navigation.KutuphanemNavigationConst
import com.mesutemre.kutuphanem.util.rememberKutuphanemAppState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            this.setKeepOnScreenCondition(object : SplashScreen.KeepOnScreenCondition {
                override fun shouldKeepOnScreen(): Boolean {
                    return viewModel.splashLoadingState.value
                }
            })
        }
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            KutuphanemTheme {
                ProvideWindowInsets {
                    val kutuphanemAppState: KutuphanemAppState = rememberKutuphanemAppState()


                    Scaffold(
                        modifier = Modifier.navigationBarsPadding(),
                        scaffoldState = kutuphanemAppState.scaffoldState,
                        snackbarHost = {
                            KutuphanemSnackBarHost(state = kutuphanemAppState.kutuphanemSnackbarState)
                        }) {

                        if (viewModel.tokenState.value.isNotEmpty()) {
                            LaunchedEffect(key1 = Unit) {
                                viewModel.snackBarErrorMessage.collect {
                                    kutuphanemAppState.showSnackbar(
                                        it.message,
                                        it.duration,
                                        it.type
                                    )
                                }
                            }
                            KutuphanemNavigation(
                                navController = kutuphanemAppState.navController,
                                startDestinition = KutuphanemNavigationConst.LOGIN_SCREEN,
                                showSnackbar = { message, duration, type ->
                                    kutuphanemAppState.showSnackbar(
                                        message = message,
                                        duration = duration,
                                        type = type
                                    )
                                }
                            )
                        } else {
                            KutuphanemNavigation(
                                navController = kutuphanemAppState.navController,
                                startDestinition = KutuphanemNavigationConst.LOGIN_SCREEN,
                                showSnackbar = { message, duration, type ->
                                    kutuphanemAppState.showSnackbar(
                                        message = message,
                                        duration = duration,
                                        type = type
                                    )
                                }
                            )
                            //KutuphanemNavigation(startDestinition = KutuphanemNavigationConst.MAIN_SCREEN)
                        }
                    }
                }

            }
        }
    }


}

@Composable
fun MainScreen(
    viewModel: MainActivityViewModel = hiltViewModel(),
    navController: NavController
) {
    Box(modifier = Modifier.fillMaxSize()) {
    }
}

