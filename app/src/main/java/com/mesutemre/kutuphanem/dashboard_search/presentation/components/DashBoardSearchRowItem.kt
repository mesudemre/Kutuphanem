package com.mesutemre.kutuphanem.dashboard_search.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mesutemre.kutuphanem.ui.theme.*

@Composable
fun DashBoardSearchHistoryRowItem(kitapAd:String,yazarAd:String,onClick:()->Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            onClick()
        }
        .padding(horizontal = 16.sdp), verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)) {
            Text(
                text = kitapAd,
                style = MaterialTheme.typography.smallUbuntuTransparentBold
            )
            Text(
                text = yazarAd,
                modifier = Modifier.padding(top = 4.sdp),
                style = MaterialTheme.typography.smallUbuntuTransparent
            )
        }
        Icon(
            Icons.Filled.ChevronRight,
            modifier = Modifier
                .padding(horizontal = 2.sdp)
                .align(alignment = Alignment.CenterVertically),
            contentDescription = kitapAd + yazarAd,
            tint = MaterialTheme.colorPalette.lacivert
        )
    }
}