package com.mesutemre.kutuphanem.kitap_liste.presentation.customcomponents

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.mesutemre.kutuphanem.kitap_liste.domain.model.SelectedListType
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem_ui.R
import com.mesutemre.kutuphanem_ui.chip.KutuphanemBaseChip

@Composable
fun KutuphanemListChipArea(
    selectedListType: SelectedListType,
    onSelect:(SelectedListType)->Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.sdp, horizontal = 16.sdp),
        horizontalArrangement = Arrangement.Center
    ) {
        KutuphanemBaseChip(
            modifier = Modifier.height(24.sdp),
            isSelected = selectedListType ==  SelectedListType.TUM_LISTE,
            selectedBorderColor = MaterialTheme.colorPalette.lacivert,
            title = "Tüm Liste",
            selectedBackgroundColor = MaterialTheme.colorPalette.lacivert,
            selectedTextColor = MaterialTheme.colorPalette.white,
            selectedIconTint = MaterialTheme.colorPalette.white,
            leadingIcon = Icons.Filled.ViewList
        ) {
            onSelect(SelectedListType.TUM_LISTE)
        }

        KutuphanemBaseChip(
            modifier = Modifier
                .height(24.sdp)
                .padding(start = 8.sdp), title = "Arşivim",
            isSelected = selectedListType ==  SelectedListType.ARSIV,
            selectedBorderColor = MaterialTheme.colorPalette.lacivert,
            selectedBackgroundColor = MaterialTheme.colorPalette.lacivert,
            selectedTextColor = MaterialTheme.colorPalette.white,
            selectedIconTint = MaterialTheme.colorPalette.white,
            leadingIcon = Icons.Filled.Archive
        ) {
            onSelect(SelectedListType.ARSIV)
        }

        KutuphanemBaseChip(
            modifier = Modifier
                .height(24.sdp)
                .padding(start = 8.sdp), title = "Beğendiklerim",
            isSelected = selectedListType ==  SelectedListType.BEGENDIKLERIM,
            selectedBorderColor = MaterialTheme.colorPalette.lacivert,
            selectedBackgroundColor = MaterialTheme.colorPalette.lacivert,
            selectedTextColor = MaterialTheme.colorPalette.white,
            selectedIconTint = MaterialTheme.colorPalette.white,
            leadingIcon = ImageVector.vectorResource(id = R.drawable.icon_like),
            leadingIconModifier = Modifier.height(16.sdp).width(16.sdp)
        ) {
            onSelect(SelectedListType.BEGENDIKLERIM)
        }
    }
}