package com.mesutemre.kutuphanem

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsPadding
import com.mesutemre.kutuphanem.navigation.KutuphanemNavigation
import com.mesutemre.kutuphanem.navigation.KutuphanemNavigationItem
import com.mesutemre.kutuphanem.navigation.getCurrentNavigationItem
import com.mesutemre.kutuphanem.navigation.isBottomNavigationTopBarVisible
import com.mesutemre.kutuphanem.ui.theme.*
import com.mesutemre.kutuphanem.util.KutuphanemAppState
import com.mesutemre.kutuphanem.util.customcomponents.snackbar.KutuphanemSnackBarHost
import com.mesutemre.kutuphanem.util.rememberKutuphanemAppState
import dagger.hilt.android.AndroidEntryPoint

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
                        floatingActionButton = {
                            if (viewModel.checkTokenExist()) {
                                KutuphanemNavigationBottomFloatingActionButton()
                            }
                        },
                        isFloatingActionButtonDocked = true,
                        floatingActionButtonPosition = FabPosition.Center,
                        topBar = {

                            if (viewModel.checkTokenExist() && kutuphanemAppState.navController.isBottomNavigationTopBarVisible()) {
                                val currentPage: KutuphanemNavigationItem? =
                                    kutuphanemAppState.navController.getCurrentNavigationItem()
                                KutuphanemTopBar(
                                    navController = kutuphanemAppState.navController,
                                    pageTitle = stringResource(
                                        id = currentPage?.pageTitle ?: R.string.anasayfaItem
                                    )
                                )
                            }
                        },
                        bottomBar = {
                            val navBackStackEntry by kutuphanemAppState.navController.currentBackStackEntryAsState()
                            val route = navBackStackEntry?.destination?.route
                            val list = KutuphanemNavigationItem::class.nestedClasses.map {
                                it.objectInstance as KutuphanemNavigationItem
                            }
                            val pageItem = list.filter {
                                it.screenRoute == route
                            }
                            if (viewModel.checkTokenExist() && kutuphanemAppState.navController.isBottomNavigationTopBarVisible(
                                    isBottomNavigation = true
                                )
                            ) {
                                KutuphanemBottomNavigationBar(kutuphanemAppState.navController)
                            }
                        },
                        snackbarHost = {
                            KutuphanemSnackBarHost(state = kutuphanemAppState.kutuphanemSnackbarState)
                        }) {
                        if (viewModel.checkTokenExist()) {
                            KutuphanemNavigation(
                                navController = kutuphanemAppState.navController,
                                startDestinition = KutuphanemNavigationItem.MainScreen,
                                //startDestinition = KutuphanemNavigationItem.LoginScreen,
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
                                startDestinition = KutuphanemNavigationItem.LoginScreen,
                                showSnackbar = { message, duration, type ->
                                    kutuphanemAppState.showSnackbar(
                                        message = message,
                                        duration = duration,
                                        type = type
                                    )
                                }
                            )
                        }
                    }
                }

            }
        }
    }
}

@Composable
private fun KutuphanemBottomNavigationBar(
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomNavItems = listOf(
        KutuphanemNavigationItem.MainScreen,
        KutuphanemNavigationItem.BookListScreen,
        KutuphanemNavigationItem.ParameterScreen,
        KutuphanemNavigationItem.ProfileScreen
    )
    BottomAppBar(
        modifier = Modifier.graphicsLayer {
            shape = RoundedCornerShape(
                topStart = 20.sdp,
                topEnd = 20.sdp
            )
            clip = true
        },
        backgroundColor = MaterialTheme.colorPalette.white,
        contentColor = MaterialTheme.colorPalette.lacivert,
        cutoutShape = RoundedCornerShape(30.sdp)
    ) {
        bottomNavItems.forEach {
            BottomNavigationItem(
                selected = currentRoute == it.screenRoute,
                icon = {
                    Icon(
                        painter = painterResource(id = it.icon ?: R.drawable.ic_baseline_home_24),
                        contentDescription = stringResource(id = R.string.anasayfaItem)
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = it.title ?: R.string.anasayfaItem),
                        style = if (currentRoute == it.screenRoute) MaterialTheme.typography.smallAllegraLacivertBold else MaterialTheme.typography.smallAllegraTransparent
                    )
                },
                selectedContentColor = MaterialTheme.colorPalette.lacivert,
                unselectedContentColor = MaterialTheme.colorPalette.transparent,
                alwaysShowLabel = true,
                onClick = {
                    Log.d("NavigationScreen", it.screenRoute)
                })
        }
    }
}

@Composable
private fun KutuphanemNavigationBottomFloatingActionButton() {
    FloatingActionButton(
        shape = RoundedCornerShape(50.sdp),
        onClick = {},
        backgroundColor = MaterialTheme.colorPalette.lacivert
    ) {
        Icon(
            Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.kitapEklemeTitle),
            tint = MaterialTheme.colorPalette.white
        )
    }
}

@Composable
fun KutuphanemTopBar(navController: NavController, pageTitle: String) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.sdp)
            .background(color = MaterialTheme.colorPalette.lacivert)
    ) {
        val (titleText, backIcon) = createRefs()
        Icon(
            Icons.Filled.ArrowBack,
            modifier = Modifier
                .constrainAs(backIcon) {
                    top.linkTo(parent.top, 40.sdp)
                    bottom.linkTo(parent.bottom, 20.sdp)
                }
                .padding(horizontal = 8.sdp)
                .clickable {
                    //navController.popBackStack()
                },
            contentDescription = pageTitle,
            tint = MaterialTheme.colorPalette.white
        )
        Text(text = pageTitle,
            modifier = Modifier
                .constrainAs(titleText) {
                    top.linkTo(parent.top, 40.sdp)
                    bottom.linkTo(parent.bottom, 20.sdp)
                    centerVerticallyTo(backIcon)
                    centerHorizontallyTo(parent)
                }
                .padding(horizontal = 8.sdp),
            style = MaterialTheme.typography.mediumUbuntuWhite)
    }
}
