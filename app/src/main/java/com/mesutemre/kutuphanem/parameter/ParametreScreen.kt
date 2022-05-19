package com.mesutemre.kutuphanem.parameter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem.util.customcomponents.menuitem.KutuphanemMenuInfo
import com.mesutemre.kutuphanem.util.customcomponents.menuitem.KutuphanemMenuItem

@Composable
fun ParametreScreen() {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = MaterialTheme.colorPalette.loginBackColor)) {
        Spacer(modifier = Modifier.height(16.sdp))
        KutuphanemMenuInfo(info = stringResource(id = R.string.parametreScreenInfo))

        Spacer(modifier = Modifier.height(16.sdp))
        KutuphanemMenuItem(label = stringResource(id = R.string.yayinEviLabel)) {

        }
        Spacer(modifier = Modifier.height(16.sdp))
        KutuphanemMenuItem(label = stringResource(id = R.string.kitapTurLabel)) {

        }
        Spacer(modifier = Modifier.height(16.sdp))
        KutuphanemMenuItem(label = stringResource(id = R.string.parameterAddLabel)) {

        }
    }
}