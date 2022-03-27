package com.mesutemre.kutuphanem

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mesutemre.kutuphanem.navigation.KutuphanemNavigation
import com.mesutemre.kutuphanem.ui.theme.KutuphanemTheme
import com.mesutemre.kutuphanem.util.navigation.KutuphanemNavigationConst
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel:MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            this.setKeepOnScreenCondition(object : SplashScreen.KeepOnScreenCondition{
                override fun shouldKeepOnScreen(): Boolean {
                    return viewModel.splashLoadingState.value
                }
            })
        }
        setContent {
            KutuphanemTheme {
                if(viewModel.tokenState.value.length>0) {
                    KutuphanemNavigation(startDestinition = KutuphanemNavigationConst.LOGIN_SCREEN)
                }else {
                    KutuphanemNavigation(startDestinition = KutuphanemNavigationConst.LOGIN_SCREEN)
                    //KutuphanemNavigation(startDestinition = KutuphanemNavigationConst.MAIN_SCREEN)
                }
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: MainActivityViewModel = hiltViewModel(),
               navController: NavController
) {
    Box(modifier = Modifier.fillMaxSize()) {
    }
}

