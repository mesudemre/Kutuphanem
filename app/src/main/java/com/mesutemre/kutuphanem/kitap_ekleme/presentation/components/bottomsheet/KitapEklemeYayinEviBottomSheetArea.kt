package com.mesutemre.kutuphanem.kitap_ekleme.presentation.components.bottomsheet

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.kitap_ekleme.domain.model.KitapEklemeYayinEviItem
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem.util.customcomponents.progressbar.KutuphanemLoader
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import com.mesutemre.kutuphanem_ui.bottomsheet.KutuphanemBottomSheetList

@Composable
fun KitapEklemeYayinEviBottomSheetArea(
    yayinEviListResponse: BaseResourceEvent<List<KitapEklemeYayinEviItem>>,
    onSelectYayinEvi: (KitapEklemeYayinEviItem?) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.84f)
    ) {
        when (yayinEviListResponse) {
            is BaseResourceEvent.Loading -> {
                KutuphanemLoader(
                    modifier = Modifier
                        .width(180.sdp)
                        .height(180.sdp)
                        .align(alignment = Alignment.CenterHorizontally)
                )
            }
            is BaseResourceEvent.Success -> {
                val yayinEviListeStr = remember {
                    derivedStateOf {
                        yayinEviListResponse.data?.map {
                            it.yayinEviAciklama
                        }
                    }
                }
                KutuphanemBottomSheetList(
                    title = stringResource(id = R.string.kitap_ekleme_bottomSheet_yayinEviTitle),
                    hasFilter = true,
                    filterHintText = stringResource(id = R.string.kitap_ekleme_bottomSheet_yayinEvi_searchFilterHint),
                    filterNotFoundText = stringResource(id = R.string.kitap_ekleme_bottomSheet_yayinEvi_searchNotFound),
                    list = yayinEviListeStr.value ?: emptyList(),
                    onSelectItem = {
                        onSelectYayinEvi(yayinEviListResponse.data?.get(it))
                    })
            }
            else -> {}
        }
    }
}