package com.mesutemre.kutuphanem.kitap_detay.presentation.components.bottomsheet

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mesutemre.kutuphanem.kitap_detay.domain.model.KitapDetayBottomSheetYorumModel
import com.mesutemre.kutuphanem.kitap_detay.domain.model.KitapDetayBottomsheetYorumListModel
import com.mesutemre.kutuphanem.kitap_detay.domain.model.KitapDetayBottomsheetYorumListType
import com.mesutemre.kutuphanem.kitap_detay.presentation.components.comments.KitapDetayBottomsheetYorumHeader
import com.mesutemre.kutuphanem.kitap_detay.presentation.components.comments.KitapYorumItem
import com.mesutemre.kutuphanem.kitap_detay.presentation.components.comments.KitapYorumYasalUyariItem
import com.mesutemre.kutuphanem.kitap_detay.presentation.components.comments.KitapYorumYazmaItem
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem.util.customcomponents.progressbar.KutuphanemLoader
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import com.mesutemre.kutuphanem_ui.bottomsheet.KutuphanemBottomSheetGesturer

@Composable
fun KitapYorumListeBottomSheet(
    yorum: String,
    kitapYorumListeResouce: BaseResourceEvent<List<KitapDetayBottomSheetYorumModel>>,
    yorumListeModel: List<KitapDetayBottomsheetYorumListModel>,
    onYorumChange: (String) -> Unit,
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
            when (kitapYorumListeResouce) {
                is BaseResourceEvent.Loading -> {
                    KutuphanemLoader(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(64.sdp)
                    )
                }
                is BaseResourceEvent.Success -> {
                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        itemsIndexed(yorumListeModel) { index, yorumRow ->
                            if (yorumRow.type == KitapDetayBottomsheetYorumListType.YAZMA_ITEM) {
                                KitapYorumYazmaItem(
                                    kullaniciResim = "",
                                    yorumText = yorum,
                                    onChangeYorum = onYorumChange
                                ) {
                                    //TODO : Burada send function call edilecek...
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
                                } ?: run {
                                    //TODO : Empty state setlenecek...
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