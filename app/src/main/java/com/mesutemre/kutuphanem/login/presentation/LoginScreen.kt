package com.mesutemre.kutuphanem.login.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.mesutemre.kutuphanem.login.presentation.components.LoginForm
import com.mesutemre.kutuphanem.login.presentation.components.LoginHeader
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.sdp

@Composable
fun LoginScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorPalette.loginBackColor)
    ) {
        LoginHeader()
        Column(modifier = Modifier.padding(top=108.sdp)) {
            LoginForm()
        }
    }
}