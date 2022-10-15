package com.mesutemre.kutuphanem.dashboard_search.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.ui.theme.mediumUbuntuTransparent
import com.mesutemre.kutuphanem.ui.theme.sdp

@Composable
fun NoSearchDoneYet(aciklama:String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.sdp, vertical = 24.sdp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.not_yet_search),
            contentDescription = aciklama
        )
        Text(
            text = aciklama,
            style = MaterialTheme.typography.mediumUbuntuTransparent,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 24.sdp)
        )
    }
}