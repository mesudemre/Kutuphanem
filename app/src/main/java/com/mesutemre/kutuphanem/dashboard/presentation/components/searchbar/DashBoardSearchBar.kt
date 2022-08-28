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
import androidx.compose.material.icons.filled.KeyboardBackspace
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.ui.theme.*

@Composable
fun DashBoardSearchBar( searchInput: String,
                        notificationCount:Int,
                        onBackPressed:()->Unit,
                        onSearch: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.sdp, end = 4.sdp, top = 8.sdp, bottom = 4.sdp)
    ) {
        var searchInputText by remember {
            mutableStateOf(searchInput)
        }
        OutlinedTextField(
            value = searchInputText,
            onValueChange = { onSearch },
            placeholder = {
                   Text(text = stringResource(id = R.string.searchHintText),
                   style = MaterialTheme.typography.mediumUbuntuTransparent)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(44.sdp)
                .clip(shape = MaterialTheme.shapes.medium)
                .background(color = MaterialTheme.colorPalette.white)
                .border(
                    width = 1.sdp,
                    color = MaterialTheme.colorPalette.otherGrayLight,
                    shape = MaterialTheme.shapes.medium
                ),
            leadingIcon = {
                if (searchInput.length>0) {
                    Icon(imageVector = Icons.Filled.KeyboardBackspace,
                        contentDescription = null,
                        tint = MaterialTheme.colorPalette.transparent,
                    modifier = Modifier.clickable {
                        onBackPressed.invoke()
                    })
                }else {
                    Icon(imageVector = Icons.Outlined.Search,
                        contentDescription = stringResource(id = R.string.searchHintText),
                        tint = MaterialTheme.colorPalette.transparent)
                }
            },
            trailingIcon = {
                if (searchInput.length>0) {
                    Icon(imageVector = Icons.Outlined.Close,
                        contentDescription = null,
                        tint = MaterialTheme.colorPalette.transparent,
                        modifier = Modifier.clickable {
                            onBackPressed.invoke()
                        })
                }else if (searchInput.length == 0 && notificationCount>0) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(imageVector = Icons.Filled.Notifications,
                            contentDescription = null,
                            tint = MaterialTheme.colorPalette.lacivert,
                            modifier = Modifier
                                .size(32.sdp)
                                .clickable {

                                })
                        Row(modifier = Modifier
                            .size(16.sdp)
                            .offset(y = (-8).sdp,x = 8.sdp)
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
        )
    }
}