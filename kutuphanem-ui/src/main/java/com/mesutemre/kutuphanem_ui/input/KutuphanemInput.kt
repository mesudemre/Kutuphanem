package com.mesutemre.kutuphanem_ui.input

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.mesutemre.kutuphanem_ui.extensions.rippleClick
import com.mesutemre.kutuphanem_ui.theme.*

@Composable
fun KutuphanemSearchInput(
    modifier: Modifier,
    searchInput: String,
    hintText: String,
    onClearSearch: () -> Unit,
    onSearch: (String) -> Unit
) {
    OutlinedTextField(
        value = searchInput,
        onValueChange = { onSearch.invoke(it) },
        placeholder = {
            Text(
                text = hintText,
                style = MaterialTheme.typography.smallUbuntuTransparent
            )
        },
        textStyle = MaterialTheme.typography.smallUbuntuBlack.copy(
            lineHeight = 12.ssp
        ),
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .height(38.sdp)
            .clip(shape = MaterialTheme.shapes.medium)
            .background(color = MaterialTheme.colorPalette.white)
            .border(
                width = 1.sdp,
                color = MaterialTheme.colorPalette.otherGrayLight,
                shape = MaterialTheme.shapes.medium
            ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = null,
                tint = MaterialTheme.colorPalette.transparent
            )
        },
        trailingIcon = {
            if (searchInput.isNotEmpty()) {
                Icon(imageVector = Icons.Filled.Close,
                    contentDescription = null,
                    tint = MaterialTheme.colorPalette.transparent,
                    modifier = Modifier.rippleClick {
                        onClearSearch()
                    })
            }
        }
    )
}