package com.mesutemre.kutuphanem.kitap_liste.presentation.customcomponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.kitap_liste.domain.model.KitapListeItem
import com.mesutemre.kutuphanem.util.customcomponents.error.KutuphanemErrorView
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import com.mesutemre.kutuphanem_ui.theme.sdp

@Composable
fun TumKitapListe(kitapServiceListeSource: BaseResourceEvent<List<KitapListeItem>>) {
    when (kitapServiceListeSource) {
        is BaseResourceEvent.Loading -> {
            KitapListeLoading()
        }
        is BaseResourceEvent.Success -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.sdp)
            ) {

            }
        }
        is BaseResourceEvent.Error -> {
            KutuphanemErrorView(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 16.sdp, horizontal = 16.sdp),
                errorText = kitapServiceListeSource.message ?: stringResource(
                    id = R.string.kitapListeEmpty
                )
            )
        }
    }
}