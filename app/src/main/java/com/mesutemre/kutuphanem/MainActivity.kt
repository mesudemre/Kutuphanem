package com.mesutemre.kutuphanem

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsPadding
import com.mesutemre.kutuphanem.navigation.KutuphanemNavigation
import com.mesutemre.kutuphanem.navigation.KutuphanemNavigationItem
import com.mesutemre.kutuphanem.navigation.getCurrentNavigationItem
import com.mesutemre.kutuphanem.navigation.isBottomNavigationTopBarVisible
import com.mesutemre.kutuphanem.ui.theme.KutuphanemTheme
import com.mesutemre.kutuphanem.ui.theme.mediumUbuntuWhite
import com.mesutemre.kutuphanem.ui.theme.smallAllegraLacivertBold
import com.mesutemre.kutuphanem.ui.theme.smallAllegraTransparent
import com.mesutemre.kutuphanem.util.KutuphanemAppState
import com.mesutemre.kutuphanem.util.customcomponents.snackbar.KutuphanemSnackBarHost
import com.mesutemre.kutuphanem.util.rememberKutuphanemAppState
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import com.mesutemre.kutuphanem_ui.extensions.rippleClick
import com.mesutemre.kutuphanem_ui.theme.colorPalette
import com.mesutemre.kutuphanem_ui.theme.sdp
import com.mesutemre.kutuphanem_ui.theme.smallUbuntuBlackBold
import com.mesutemre.kutuphanem_ui.theme.ssp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var mainActivityState: State<MainActivityState>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenStarted {
            viewModel.messageFlow.collect {
                Log.d("MESAJ", "Mesaj geldi!!!")
            }
        }
        WindowCompat.setDecorFitsSystemWindows(window, false)
        actionBar?.hide()
        setContent {
            KutuphanemTheme {
                ProvideWindowInsets {
                    val kutuphanemAppState: KutuphanemAppState = rememberKutuphanemAppState()
                    mainActivityState = viewModel.mainActivityState.collectAsState()
                    installSplashScreen().apply {
                        this.setKeepOnScreenCondition(object : SplashScreen.KeepOnScreenCondition {
                            override fun shouldKeepOnScreen(): Boolean {
                                return mainActivityState.value.tokenResourceEvent is BaseResourceEvent.Loading
                            }
                        })
                    }
                    Scaffold(modifier = Modifier.navigationBarsPadding(),
                        scaffoldState = kutuphanemAppState.scaffoldState,
                        floatingActionButton = {
                            if (kutuphanemAppState.navController.isBottomNavigationTopBarVisible(
                                    isBottomNavigation = true
                                )
                            ) {
                                KutuphanemNavigationBottomFloatingActionButton(mainActivityState.value.animateMenuVisibility) {
                                    viewModel.activateMenuAnimation()
                                }
                            }
                        },
                        isFloatingActionButtonDocked = true,
                        floatingActionButtonPosition = FabPosition.Center,
                        topBar = {
                            if (kutuphanemAppState.navController.isBottomNavigationTopBarVisible()) {
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
                            if (kutuphanemAppState.navController.isBottomNavigationTopBarVisible(
                                    isBottomNavigation = true
                                )
                            ) {
                                KutuphanemBottomNavigationBar(kutuphanemAppState.navController)
                            }
                        },
                        snackbarHost = {
                            KutuphanemSnackBarHost(state = kutuphanemAppState.kutuphanemSnackbarState)
                        }) {
                        when (mainActivityState.value.tokenResourceEvent) {
                            is BaseResourceEvent.Success -> {
                                mainActivityState.value.tokenResourceEvent.data?.let {
                                    if (it.isNotEmpty()) {
                                        Box(modifier = Modifier.fillMaxSize()) {
                                            KutuphanemNavigation(navController = kutuphanemAppState.navController,
                                                startDestinition = KutuphanemNavigationItem.DashboardScreen,
                                                showSnackbar = { message, duration, type ->
                                                    kutuphanemAppState.showSnackbar(
                                                        message = message,
                                                        duration = duration,
                                                        type = type
                                                    )
                                                })
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .align(Alignment.BottomCenter)
                                                    .padding(64.sdp)
                                                    .height(if (mainActivityState.value.animateMenuVisibility) 80.sdp else 0.sdp)
                                                    .border(
                                                        1.sdp,
                                                        color = MaterialTheme.colorPalette.secondaryGray,
                                                        shape = MaterialTheme.shapes.medium
                                                    )
                                                    .clip(shape = MaterialTheme.shapes.medium)
                                                    .background(
                                                        MaterialTheme.colorPalette.white
                                                    ),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                FloatingActionMenu(mainActivityState.value.animateMenuVisibility)
                                            }

                                        }
                                    } else {
                                        KutuphanemNavigation(navController = kutuphanemAppState.navController,
                                            startDestinition = KutuphanemNavigationItem.LoginScreen,
                                            showSnackbar = { message, duration, type ->
                                                kutuphanemAppState.showSnackbar(
                                                    message = message,
                                                    duration = duration,
                                                    type = type
                                                )
                                            })
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }
    }

    @Composable
    fun FloatingActionMenu(menuItemAnim: Boolean) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.sdp),
        ) {
            AnimatedVisibility(
                visible = menuItemAnim,
                enter = slideInHorizontally(animationSpec = tween(durationMillis = 300)) { fullWidth ->
                    -fullWidth / 3
                } + fadeIn(
                    animationSpec = tween(durationMillis = 300)
                ),
                exit = slideOutHorizontally(animationSpec = spring(stiffness = Spring.StiffnessHigh)) {
                    300
                } + fadeOut()
            ) {
                FloatingActionMenuItem(
                    modifier = Modifier.padding(start = 4.sdp),
                    iconId = R.drawable.ic_baseline_add_24,
                    aciklama = stringResource(
                        id = R.string.kitapEklemeTitle
                    )
                ) {}
            }
            Divider(
                modifier = Modifier.padding(
                    horizontal = 4.sdp,
                    vertical = 8.sdp,
                ),
                thickness = 1.sdp,
                color = MaterialTheme.colorPalette.otherGrayLight
            )
            AnimatedVisibility(
                visible = menuItemAnim,
                enter = slideInHorizontally(animationSpec = tween(durationMillis = 350)) { fullWidth ->
                    -fullWidth / 3
                } + fadeIn(
                    animationSpec = tween(durationMillis = 350)
                ),
                exit = slideOutHorizontally(animationSpec = spring(stiffness = Spring.StiffnessHigh)) {
                    350
                } + fadeOut()
            ) {
                FloatingActionMenuItem(
                    modifier = Modifier.padding(start = 4.sdp),
                    iconId = R.drawable.ic_barcode,
                    aciklama = stringResource(
                        id = R.string.floating_action_menu_barcode
                    )
                ) {}
            }
        }
    }

    @Composable
    private fun FloatingActionMenuItem(
        modifier: Modifier, @DrawableRes iconId: Int, aciklama: String, onClick: () -> Unit
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onClick()
                }, verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(
                    id = iconId
                ),
                tint = MaterialTheme.colorPalette.black,
                contentDescription = stringResource(id = R.string.anasayfaItem)
            )
            Text(
                text = aciklama,
                modifier = modifier.weight(1f),
                style = MaterialTheme.typography.smallUbuntuBlackBold.copy(
                    lineHeight = 14.ssp
                )
            )
        }
    }

    @Composable
    private fun KutuphanemBottomNavigationBar(
        navController: NavController
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val bottomNavItems = listOf(
            KutuphanemNavigationItem.DashboardScreen,
            KutuphanemNavigationItem.BookListScreen,
            KutuphanemNavigationItem.ParameterScreen,
            KutuphanemNavigationItem.ProfileScreen
        )
        BottomAppBar(
            backgroundColor = MaterialTheme.colorPalette.white,
            contentColor = MaterialTheme.colorPalette.lacivert,
            cutoutShape = RoundedCornerShape(30.sdp)
        ) {
            bottomNavItems.forEach {
                BottomNavigationItem(selected = currentRoute == it.screenRoute,
                    icon = {
                        Icon(
                            painter = painterResource(
                                id = it.icon ?: R.drawable.ic_baseline_home_24
                            ), contentDescription = stringResource(id = R.string.anasayfaItem)
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
                        viewModel.closeFastTransactionMenu()
                        navController.navigate(it.screenRoute) {
                            navController.graph.startDestinationRoute?.let { screen_route ->
                                popUpTo(screen_route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    })
            }
        }
    }

    @Composable
    private fun KutuphanemNavigationBottomFloatingActionButton(
        isVisible: Boolean, onClick: () -> Unit
    ) {
        val floatTweenSpec: TweenSpec<Float> = tween(durationMillis = 300)
        val colorTweenSpec: TweenSpec<Color> = tween(durationMillis = 300)
        val animatedIconFGColor by animateColorAsState(
            targetValue = if (isVisible) MaterialTheme.colorPalette.kirmizi else MaterialTheme.colorPalette.lacivert,
            animationSpec = colorTweenSpec
        )
        val animatedRotateAngle by animateFloatAsState(
            targetValue = if (isVisible) 135.0f else 0.0f, animationSpec = floatTweenSpec
        )

        FloatingActionButton(
            shape = RoundedCornerShape(50.sdp), onClick = onClick, backgroundColor = animatedIconFGColor
        ) {
            Icon(
                Icons.Filled.Add,
                modifier = Modifier.rotate(animatedRotateAngle),
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
            Icon(Icons.Filled.ArrowBack, modifier = Modifier
                .constrainAs(backIcon) {
                    top.linkTo(parent.top, 40.sdp)
                    bottom.linkTo(parent.bottom, 20.sdp)
                }
                .padding(horizontal = 8.sdp)
                .rippleClick {
                    navController.popBackStack()
                }, contentDescription = pageTitle, tint = MaterialTheme.colorPalette.white
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

    override fun onBackPressed() {
        if (mainActivityState.value.animateMenuVisibility) {
            viewModel.closeFastTransactionMenu()
        } else {
            super.onBackPressed()
        }
    }
}


