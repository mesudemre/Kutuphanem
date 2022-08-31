package com.mesutemre.kutuphanem.dashboard.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.ui.theme.*

@Composable
fun DashBoardSearchBar(
                        notificationCount:Int,
                        onClickSearch:()->Unit,
                        onSearch: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.sdp)
    ) {
        OutlinedTextField(
            value = "",
            onValueChange = { onSearch.invoke(it) },
            placeholder = {
                Text(text = stringResource(id = R.string.searchHintText),
                    style = MaterialTheme.typography.smallUbuntuTransparent)
            },
            textStyle = MaterialTheme.typography.normalUbuntuBlack,
            enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .height(40.sdp)
                .weight(0.9f)
                .clip(shape = MaterialTheme.shapes.medium)
                .background(color = MaterialTheme.colorPalette.white)
                .border(
                    width = 1.sdp,
                    color = MaterialTheme.colorPalette.otherGrayLight,
                    shape = MaterialTheme.shapes.medium
                )
                .clickable {
                    onClickSearch.invoke()
                },
            leadingIcon = {
                    Icon(imageVector = Icons.Outlined.Search,
                        contentDescription = stringResource(id = R.string.searchHintText),
                        tint = MaterialTheme.colorPalette.transparent)
            }
        )

        Box(modifier = Modifier.padding(start = 4.sdp),
            contentAlignment = Alignment.Center) {
            Icon(imageVector = Icons.Filled.Notifications,
                contentDescription = null,
                tint = MaterialTheme.colorPalette.lacivert,
                modifier = Modifier
                    .size(40.sdp)
                    .clickable {

                    })
            Row(modifier = Modifier
                .size(16.sdp)
                .offset(y = (-8).sdp, x = 8.sdp)
                .clip(shape = CircleShape)
                .aspectRatio(1f)
                .background(color = MaterialTheme.colorPalette.turuncu),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = notificationCount.toString(),
                    style = MaterialTheme.typography.smallUbuntuWhite.copy(
                        fontSize = 10.ssp
                    ))
            }
        }
    }
}