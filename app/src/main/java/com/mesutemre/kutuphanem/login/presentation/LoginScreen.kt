package com.mesutemre.kutuphanem.login.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.login.presentation.components.LoginForm
import com.mesutemre.kutuphanem.login.presentation.components.LoginHeader
import com.mesutemre.kutuphanem.ui.theme.*

@Composable
fun LoginScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorPalette.loginBackColor)
    ) {
        LoginHeader()
        Column(modifier = Modifier.padding(top=96.sdp)) {
            LoginForm()
        }
    }
}