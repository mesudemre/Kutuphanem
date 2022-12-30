package com.mesutemre.kutuphanem.kitap_detay.presentation.components.comments

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.ui.theme.mediumUbuntuBlackBold
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem_ui.extensions.rippleClick
import com.mesutemre.kutuphanem_ui.theme.colorPalette

@Composable
fun KitapDetayBottomsheetYorumHeader(
    onCloseSheet: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.sdp, horizontal = 16.sdp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.kitapYorumlarText),
            style = MaterialTheme.typography.mediumUbuntuBlackBold,
            modifier = Modifier.weight(1f)
        )
        Icon(
            Icons.Filled.Close,
            modifier = Modifier
                .size(24.sdp)
                .rippleClick {
                    onCloseSheet()
                },
            contentDescription = stringResource(id = R.string.kitapYorumlarText),
            tint = MaterialTheme.colorPalette.black
        )
    }
}