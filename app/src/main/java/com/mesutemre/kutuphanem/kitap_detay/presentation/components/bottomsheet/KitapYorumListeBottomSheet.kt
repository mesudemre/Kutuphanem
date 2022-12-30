package com.mesutemre.kutuphanem.kitap_detay.presentation.components.bottomsheet

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mesutemre.kutuphanem.kitap_detay.domain.model.KitapDetayBottomSheetYorumModel
import com.mesutemre.kutuphanem.kitap_detay.domain.model.KitapDetayBottomsheetYorumListModel
import com.mesutemre.kutuphanem.kitap_detay.domain.model.KitapDetayBottomsheetYorumListType
import com.mesutemre.kutuphanem.kitap_detay.presentation.components.comments.KitapDetayBottomsheetYorumHeader
import com.mesutemre.kutuphanem.kitap_detay.presentation.components.comments.KitapYorumItem
import com.mesutemre.kutuphanem.kitap_detay.presentation.components.comments.KitapYorumYasalUyariItem
import com.mesutemre.kutuphanem.kitap_detay.presentation.components.comments.KitapYorumYazmaItem
import com.mesutemre.kutuphanem.model.ResponseStatusModel
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem.util.customcomponents.progressbar.KutuphanemLoader
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import com.mesutemre.kutuphanem_ui.bottomsheet.KutuphanemBottomSheetGesturer
import com.mesutemre.kutuphanem_ui.theme.colorPalette

@Composable
fun KitapYorumListeBottomSheet(
    yorum: String,
    kullaniciResim: String,
    kitapYorumListeResource: BaseResourceEvent<List<KitapDetayBottomSheetYorumModel>>,
    kitapYorumKaydetResource: BaseResourceEvent<ResponseStatusModel?>,
    yorumListeModel: List<KitapDetayBottomsheetYorumListModel>,
    getKitapYorumListe: () -> Unit,
    onYorumChange: (String) -> Unit,
    kitapYorumKaydet: (String) -> Unit,
    onCloseBottomSheet: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.92f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.sdp, horizontal = 16.sdp),
            horizontalArrangement = Arrangement.Center
        ) {
            KutuphanemBottomSheetGesturer()
        }
        KitapDetayBottomsheetYorumHeader {
            onCloseBottomSheet()
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.sdp, vertical = 8.sdp)
        ) {
            if (kitapYorumKaydetResource is BaseResourceEvent.Loading) {
                KutuphanemLoader(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(128.sdp)
                )
            }
            when (kitapYorumListeResource) {
                is BaseResourceEvent.Loading -> {
                    KutuphanemLoader(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(128.sdp)
                    )
                }
                is BaseResourceEvent.Success -> {
                    SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing = kitapYorumListeResource is BaseResourceEvent.Loading),
                        onRefresh = {
                            getKitapYorumListe()
                        },
                        indicator = { state, trigger ->
                            SwipeRefreshIndicator(
                                state = state,
                                refreshTriggerDistance = trigger,
                                scale = true,
                                backgroundColor = MaterialTheme.colorPalette.white,
                                contentColor = MaterialTheme.colorPalette.lacivert,
                                shape = RoundedCornerShape(50.sdp),
                            )
                        }
                    ) {
                        LazyColumn(modifier = Modifier.fillMaxWidth()) {
                            itemsIndexed(yorumListeModel) { index, yorumRow ->
                                if (yorumRow.type == KitapDetayBottomsheetYorumListType.YAZMA_ITEM) {
                                    KitapYorumYazmaItem(
                                        kullaniciResim = kullaniciResim,
                                        yorumText = yorum,
                                        onChangeYorum = onYorumChange
                                    ) {
                                        kitapYorumKaydet(it)
                                    }
                                }
                                if (yorumRow.type == KitapDetayBottomsheetYorumListType.YASAL_UYARI_ITEM) {
                                    Spacer(modifier = Modifier.height(8.sdp))
                                    KitapYorumYasalUyariItem()
                                }
                                if (yorumRow.type == KitapDetayBottomsheetYorumListType.YORUM_ITEM) {
                                    val yorum = yorumRow.data as KitapDetayBottomSheetYorumModel?
                                    yorum?.let {
                                        Spacer(modifier = Modifier.height(8.sdp))
                                        KitapYorumItem(yorumItem = yorum)
                                        Divider(
                                            modifier = Modifier.padding(
                                                vertical = 4.sdp,
                                            ),
                                            thickness = 1.sdp,
                                            color = MaterialTheme.colorPalette.otherGrayLight
                                        )
                                    } ?: run {
                                        //TODO : Empty state setlenecek...
                                    }
                                }
                            }
                        }
                    }
                }
                is BaseResourceEvent.Error -> {

                }
            }
        }
    }
}