package com.mesutemre.kutuphanem

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mesutemre.kutuphanem.navigation.KutuphanemNavigation
import com.mesutemre.kutuphanem.ui.theme.KutuphanemTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel:MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            this.setKeepOnScreenCondition(object : SplashScreen.KeepOnScreenCondition{
                override fun shouldKeepOnScreen(): Boolean {
                    return viewModel.state.value.isLoading
                }
            })
        }
        setContent {
            KutuphanemTheme {
                KutuphanemNavigation()
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: MainActivityViewModel = hiltViewModel(),
               navController: NavController = rememberNavController()
) {
    Box(modifier = Modifier.fillMaxSize()){
        if (viewModel.state.value.token != null) {
            Log.d("Token","Token null değil")
        }else {
            Log.d("Token","Token null")
        }
        Text(text = "MainCompose Activityden selamlar....")
    }
}

