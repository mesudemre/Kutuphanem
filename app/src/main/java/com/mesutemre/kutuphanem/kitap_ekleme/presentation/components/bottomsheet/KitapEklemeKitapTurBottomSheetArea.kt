package com.mesutemre.kutuphanem.kitap_ekleme.presentation.components.bottomsheet

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.kitap_ekleme.domain.model.KitapEklemeKitapTurItem
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem.util.customcomponents.progressbar.KutuphanemLoader
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import com.mesutemre.kutuphanem_ui.bottomsheet.KutuphanemBottomSheetList

@Composable
fun KitapEklemeKitapTurBottomSheetArea(
    kitapTurListResponse: BaseResourceEvent<List<KitapEklemeKitapTurItem>>,
    onSelectKitapTur: (KitapEklemeKitapTurItem?) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.84f)
    ) {
        when (kitapTurListResponse) {
            is BaseResourceEvent.Loading -> {
                KutuphanemLoader(
                    modifier = Modifier
                        .width(180.sdp)
                        .height(180.sdp)
                        .align(alignment = Alignment.CenterHorizontally)
                )
            }
            is BaseResourceEvent.Success -> {
                val kitapTurListeStr = remember {
                    derivedStateOf {
                        kitapTurListResponse.data?.map {
                            it.kitapTurAciklama
                        }
                    }
                }
                KutuphanemBottomSheetList(
                    title = stringResource(id = R.string.kitap_ekleme_bottomSheet_kitapTurTitle),
                    hasFilter = true,
                    filterHintText = stringResource(id = R.string.kitap_ekleme_bottomSheet_kitapTur_searchFilterHint),
                    filterNotFoundText = stringResource(id = R.string.kitap_ekleme_bottomSheet_kitapTur_searchNotFound),
                    list = kitapTurListeStr.value ?: emptyList(),
                    onSelectItem = {
                        onSelectKitapTur(kitapTurListResponse.data?.get(it))
                    })
            }
            else -> {}
        }
    }
}