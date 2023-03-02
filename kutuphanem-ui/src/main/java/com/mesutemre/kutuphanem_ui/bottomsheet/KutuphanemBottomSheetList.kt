package com.mesutemre.kutuphanem_ui.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import com.mesutemre.kutuphanem_ui.input.KutuphanemSearchInput
import com.mesutemre.kutuphanem_ui.theme.colorPalette
import com.mesutemre.kutuphanem_ui.theme.sdp
import com.mesutemre.kutuphanem_ui.theme.smallUbuntuBlackBold
import com.mesutemre.kutuphanem_ui.theme.smallUbuntuTransparent

@Preview
@Composable
fun KutuphanemBottomSheetListPreview(
    title: String = "Yayınevi Listesi",
    list: List<String> = listOf("Ötüken Neşriyat,Dergah Yayınları")
) {
    KutuphanemBottomSheetList(title = title, list = list, onSelectItem = {})
}

@Composable
fun KutuphanemBottomSheetList(
    title: String,
    hasFilter: Boolean = false,
    filterHintText: String? = null,
    filterNotFoundText: String? = null,
    list: List<String>,
    onSelectItem: (Int) -> Unit
) {
    var searchText by remember {
        mutableStateOf("")
    }
    val filteredList = remember {
        derivedStateOf {
            if (searchText.isEmpty()) list
            else list.filter {
                it.toLowerCase(Locale.current).contains(searchText.toLowerCase(Locale.current))
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorPalette.loginBackColor)
    ) {
        Row(
            modifier = Modifier
                .padding(top = 12.sdp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.Center
        ) {
            KutuphanemBottomSheetGesturer()
        }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.sdp, top = 12.sdp),
            text = title,
            style = MaterialTheme.typography.smallUbuntuBlackBold
        )
        Divider(
            modifier = Modifier.padding(vertical = 8.sdp),
            thickness = (1 / 2).sdp,
            color = MaterialTheme.colorPalette.otherGrayLight
        )
        if (hasFilter) {
            KutuphanemSearchInput(
                modifier = Modifier.padding(horizontal = 12.sdp),
                searchInput = searchText,
                hintText = filterHintText ?: "",
                onClearSearch = {
                    searchText = ""
                },
                onSearch = {
                    searchText = it
                }
            )
            Divider(
                modifier = Modifier.padding(vertical = 8.sdp),
                thickness = (1 / 2).sdp,
                color = MaterialTheme.colorPalette.otherGrayLight
            )
        }
        if (searchText.isNotEmpty() && filteredList.value.isEmpty()) {
            KutuphanemBottomSheetEmptyState(aciklama = filterNotFoundText ?: "")
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = if (hasFilter) 4.sdp else 12.sdp)
        ) {

            itemsIndexed(filteredList.value, key = { index, item ->
                index
            }) { index, item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.sdp)
                        .clickable {
                            onSelectItem(list.indexOf(item))
                        },
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = item,
                        style = MaterialTheme.typography.smallUbuntuTransparent
                    )
                    Icon(
                        Icons.Filled.ChevronRight,
                        contentDescription = item,
                        tint = MaterialTheme.colorPalette.lacivert
                    )
                }
                Divider(
                    modifier = Modifier.padding(vertical = 8.sdp, horizontal = 16.sdp),
                    thickness = 1.sdp,
                    color = MaterialTheme.colorPalette.otherGrayLight
                )
            }
        }
    }
}