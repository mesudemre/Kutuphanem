package com.mesutemre.kutuphanem.login.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.mesutemre.kutuphanem.login.presentation.components.LoginForm
import com.mesutemre.kutuphanem.login.presentation.components.LoginHeader
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem.util.customcomponents.snackbar.KutuphanemSnackBarHost

@Composable
fun LoginScreen(loginViewModel: LoginViewModel = hiltViewModel()) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(scaffoldState = scaffoldState,
        snackbarHost = { hostState ->
            KutuphanemSnackBarHost(
                snackbarHostState = hostState,
                type = loginViewModel.state.value.snackBarState
            )
        },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorPalette.loginBackColor)
            ) {
                LoginHeader()
                Column(
                    modifier = Modifier
                        .padding(top = 108.sdp)
                        .verticalScroll(rememberScrollState())
                ) {
                    LoginForm(scaffoldState.snackbarHostState)
                }
            }
        })
}