package com.mesutemre.kutuphanem.kitap_detay.presentation.components.body

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.kitap_detay.domain.model.KitapDetayItem
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.normalUbuntuTransparentBold
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import com.mesutemre.kutuphanem_ui.card.KitapDetayInfoCard

@Composable
fun KitapDetayInfoBodyArea(
    kitapDetayItemResource: BaseResourceEvent<KitapDetayItem>
) {
    when (kitapDetayItemResource) {
        is BaseResourceEvent.Loading -> {

        }
        is BaseResourceEvent.Success -> {
            val kitapDetayData = kitapDetayItemResource.data
            Column(modifier = Modifier.padding(horizontal = 16.sdp, vertical = 10.sdp)) {
                Text(
                    text = "KİTAP BİLGİSİ",
                    modifier = Modifier.padding(bottom = 8.sdp),
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.normalUbuntuTransparentBold
                )
                KitapDetayInfoCard(
                    label = stringResource(id = R.string.kitapAdLabelText),
                    value = kitapDetayData?.kitapAd ?: ""
                )
                KitapDetayInfoDivider(4)
                KitapDetayInfoCard(
                    label = stringResource(id = R.string.yazarAdLabelText),
                    value = kitapDetayData?.yazarAd ?: ""
                )
                KitapDetayInfoDivider(4)
                KitapDetayInfoCard(
                    label = stringResource(id = R.string.alinmaTarLabelText),
                    value = kitapDetayData?.alinmaTar.toString()
                )
                KitapDetayInfoDivider(4)
                KitapDetayInfoCard(
                    label = stringResource(id = R.string.kitapTurLabel),
                    value = kitapDetayData?.kitapTurAd ?: ""
                )
                KitapDetayInfoDivider(4)
                KitapDetayInfoCard(
                    label = stringResource(id = R.string.yayinEviLabel),
                    value = kitapDetayData?.yayinEviAd ?: ""
                )
                KitapDetayInfoDivider(4)
            }
        }
        is BaseResourceEvent.Error -> {

        }
    }
}

@Composable
private fun KitapDetayInfoDivider(paddingVertical: Int) {
    Divider(
        modifier = Modifier.padding(vertical = paddingVertical.sdp),
        thickness = 1.sdp,
        color = MaterialTheme.colorPalette.otherGrayLight
    )
}