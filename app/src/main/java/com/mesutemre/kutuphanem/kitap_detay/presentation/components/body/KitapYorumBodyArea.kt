package com.mesutemre.kutuphanem.kitap_detay.presentation.components.body

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.kitap_detay.domain.model.KitapDetayIlkYorumModel
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem_ui.theme.smallUbuntuBlackBold
import com.mesutemre.kutuphanem_ui.theme.smallUbuntuErrorBold
import com.mesutemre.kutuphanem_ui.theme.smallUbuntuTransparent

@Composable
fun KitapYorumBodyArea(
    yorumSayisi: Int,
    kitapDetayIlkYorumModel: KitapDetayIlkYorumModel? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.sdp),
        shape = MaterialTheme.shapes.small,
        backgroundColor = MaterialTheme.colorPalette.white,
        elevation = 4.sdp
    ) {
        kitapDetayIlkYorumModel?.let {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.sdp, vertical = 8.sdp)
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = stringResource(id = R.string.kitap_detay_ilkYorumTitle),
                        style = MaterialTheme.typography.smallUbuntuBlackBold
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.sdp),
                        text = "$yorumSayisi",
                        style = MaterialTheme.typography.smallUbuntuTransparent
                    )
                }
                KitapIlkYorumItem(kitapDetayIlkYorumModel = kitapDetayIlkYorumModel)
            }
        } ?: run {
            Text(
                modifier = Modifier.padding(horizontal = 8.sdp, vertical = 8.sdp),
                text = stringResource(id = R.string.noComentKitapYet),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.smallUbuntuErrorBold
            )
        }

    }
}