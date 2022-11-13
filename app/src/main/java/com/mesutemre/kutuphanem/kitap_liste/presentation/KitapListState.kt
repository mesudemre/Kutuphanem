package com.mesutemre.kutuphanem.kitap_liste.presentation

import androidx.paging.PagingData
import com.mesutemre.kutuphanem.kitap_liste.domain.model.KitapArsivItem
import com.mesutemre.kutuphanem.kitap_liste.domain.model.KitapListeItem
import com.mesutemre.kutuphanem.kitap_liste.domain.model.SelectedListType
import com.mesutemre.kutuphanem.model.ResponseStatusModel
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

data class KitapListState(
    val selectedListType: SelectedListType = SelectedListType.TUM_LISTE,
    val kitapArsivListeSource: BaseResourceEvent<List<KitapArsivItem>> = BaseResourceEvent.Loading(),
    val kitapListItemPageData: Flow<PagingData<KitapListeItem>> = flow { },
    val kitapArsivleSource: BaseResourceEvent<ResponseStatusModel> = BaseResourceEvent.Nothing()
)
