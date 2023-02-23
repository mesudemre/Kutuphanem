package com.mesutemre.kutuphanem.kitap_liste.presentation.customcomponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.kitap_liste.domain.model.KitapArsivItem
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import com.mesutemre.kutuphanem_ui.card.KitapArsivItemCard
import com.mesutemre.kutuphanem_ui.theme.sdp

@Composable
fun KitapArsivListe(
    arsivListState: LazyListState,
    kitapArsivListeSource: BaseResourceEvent<List<KitapArsivItem>>) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.sdp)
    ) {
        when (kitapArsivListeSource) {
            is BaseResourceEvent.Loading -> {
                KitapListeLoading()
            }
            is BaseResourceEvent.Success -> {
                kitapArsivListeSource.data?.let {
                    if (it.isNotEmpty()) {
                        LazyColumn(modifier = Modifier.padding(bottom = 20.sdp), state = arsivListState) {
                            items(it) { kitap ->
                                KitapArsivItemCard(
                                    kitapAd = kitap.kitapAd,
                                    yazarAd = kitap.yazarAd,
                                    aciklama = kitap.kitapAciklama,
                                    kitapResim = kitap.kitapResim
                                )
                                Spacer(modifier = Modifier.padding(top = 12.sdp))
                            }
                        }
                    } else {
                        KitapListeEmptyArea(stringResource(id = R.string.kitapArsivMevcutDegil))
                    }
                }

            }
            is BaseResourceEvent.Error -> {
            }
            else -> {}
        }
    }

}