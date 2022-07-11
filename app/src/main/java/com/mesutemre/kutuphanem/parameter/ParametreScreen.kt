package com.mesutemre.kutuphanem.parameter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.navigation.KutuphanemNavigationItem
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem.util.customcomponents.menuitem.KutuphanemMenuInfo
import com.mesutemre.kutuphanem.util.customcomponents.menuitem.KutuphanemMenuItem

@Composable
fun ParametreScreen(navController: NavController) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = MaterialTheme.colorPalette.loginBackColor)) {
        Spacer(modifier = Modifier.height(16.sdp))
        KutuphanemMenuInfo(info = stringResource(id = R.string.parametreScreenInfo))

        Spacer(modifier = Modifier.height(16.sdp))
        KutuphanemMenuItem(label = stringResource(id = R.string.yayinEviLabel)) {
            navController.navigate(KutuphanemNavigationItem.ParameterYayinEviScreen.screenRoute)
        }
        Spacer(modifier = Modifier.height(16.sdp))
        KutuphanemMenuItem(label = stringResource(id = R.string.kitapTurLabel)) {
            navController.navigate(KutuphanemNavigationItem.ParameterKitapTurScreen.screenRoute)
        }
        Spacer(modifier = Modifier.height(16.sdp))
        KutuphanemMenuItem(label = stringResource(id = R.string.parameterAddLabel)) {
            navController.navigate(KutuphanemNavigationItem.ParameterEklemeScreen.screenRoute)
        }
    }
}