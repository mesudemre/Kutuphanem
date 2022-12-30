package com.mesutemre.kutuphanem.kitap_detay.presentation.components.bottomsheet

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem_ui.bottomsheet.KutuphanemBottomSheetGesturer
import com.mesutemre.kutuphanem_ui.theme.normalUbuntuTransparent

@Composable
fun KitapDetayAciklamaBottomSheet(kitapAciklama: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.92f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.sdp),
            horizontalArrangement = Arrangement.Center
        ) {
            KutuphanemBottomSheetGesturer()
        }
        Text(
            text = kitapAciklama,
            modifier = Modifier
                .padding(vertical = 4.sdp, horizontal = 16.sdp)
                .verticalScroll(
                    rememberScrollState()
                ),
            style = MaterialTheme.typography.normalUbuntuTransparent
        )
    }
}