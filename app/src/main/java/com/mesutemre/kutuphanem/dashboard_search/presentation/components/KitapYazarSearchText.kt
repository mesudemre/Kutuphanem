package com.mesutemre.kutuphanem.dashboard_search.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardBackspace
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem.ui.theme.smallUbuntuBlack
import com.mesutemre.kutuphanem.ui.theme.smallUbuntuTransparent

@Composable
fun KitapYazarSearchText(searchInput: String,
                         onBackPressed:()->Unit,
                         onClearSearch:()->Unit,
                         onSearch: (String) -> Unit) {
    val focusRequester = remember { FocusRequester() }

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(8.sdp)) {
        OutlinedTextField(
            value = searchInput,
            onValueChange = { onSearch.invoke(it) },
            placeholder = {
                Text(text = stringResource(id = R.string.searchHintText),
                    style = MaterialTheme.typography.smallUbuntuTransparent)
            },
            textStyle = MaterialTheme.typography.smallUbuntuBlack,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 40.sdp)
                .clip(shape = MaterialTheme.shapes.medium)
                .background(color = MaterialTheme.colorPalette.white)
                .border(
                    width = 1.sdp,
                    color = MaterialTheme.colorPalette.otherGrayLight,
                    shape = MaterialTheme.shapes.medium
                )
                .focusRequester(focusRequester),
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
                            onClearSearch()
                        })
                }
            }
        )
    }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}