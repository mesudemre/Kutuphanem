package com.mesutemre.kutuphanem.kitap_liste.presentation

import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.kitap_liste.domain.model.KitapListeItem
import com.mesutemre.kutuphanem.kitap_liste.domain.model.SelectedListType

data class KitapListState(
    val selectedListType: SelectedListType = SelectedListType.TUM_LISTE,
    val kitapServiceListeSource: BaseResourceEvent<List<KitapListeItem>> = BaseResourceEvent.Loading()
)
