package com.mesutemre.kutuphanem.kitap_detay.presentation.components.body

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.style.TextOverflow
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.kitap_detay.domain.model.KitapDetayBottomSheetState
import com.mesutemre.kutuphanem.kitap_detay.domain.model.KitapDetayItem
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.normalUbuntuTransparentBold
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem.util.convertDate2String
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import com.mesutemre.kutuphanem_ui.card.KitapDetayInfoCard
import com.mesutemre.kutuphanem_ui.extensions.rippleClick
import com.mesutemre.kutuphanem_ui.theme.smallUbuntuBlackBold
import com.mesutemre.kutuphanem_ui.theme.smallUbuntuTransparent
import com.mesutemre.kutuphanem_ui.theme.ssp

@Composable
fun KitapDetayInfoBodyArea(
    kitapDetayItemResource: BaseResourceEvent<KitapDetayItem>,
    changeBottomSheetState: (String) -> Unit
) {

    when (kitapDetayItemResource) {
        is BaseResourceEvent.Loading -> {

        }
        is BaseResourceEvent.Success -> {
            val kitapDetayData = kitapDetayItemResource.data
            Column(modifier = Modifier.padding(horizontal = 16.sdp, vertical = 10.sdp)) {
                Text(
                    text = stringResource(id = R.string.kitap_detay_kitap_bilgi_label),
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
                    value = kitapDetayData?.alinmaTar?.convertDate2String() ?: ""
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
                KitapAciklamaText(
                    label = stringResource(id = R.string.aciklamaHintText),
                    aciklama = kitapDetayData?.kitapAciklama ?: ""
                ) {
                    changeBottomSheetState.invoke(kitapDetayData?.kitapAciklama ?: "")
                }
                KitapDetayInfoDivider(4)
                KitapDetayRatingArea(kitapPuan = kitapDetayData?.kitapPuan ?: 0f)
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

@Composable
private fun KitapAciklamaText(
    label: String,
    aciklama: String,
    onClickTextDetailIcon: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        var lineCount by remember {
            mutableStateOf(1)
        }
        Text(
            text = label,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.smallUbuntuBlackBold
        )
        Text(
            text = aciklama,
            modifier = Modifier
                .padding(top = 4.sdp),
            maxLines = 4,
            onTextLayout = { textLayoutResult: TextLayoutResult ->
                lineCount = textLayoutResult.lineCount
            },
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.smallUbuntuTransparent.copy(lineHeight = 14.ssp)
        )
        if (lineCount > 3) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown, contentDescription = label,
                tint = MaterialTheme.colorPalette.transparent,
                modifier = Modifier
                    .size(32.sdp)
                    .align(Alignment.CenterHorizontally)
                    .rippleClick {
                        onClickTextDetailIcon()
                    }
            )
        }
    }
}