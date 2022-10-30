package com.mesutemre.kutuphanem.kitap_liste.presentation

import com.mesutemre.kutuphanem.kitap_liste.domain.model.KitapListeItem
import com.mesutemre.kutuphanem.kitap_liste.domain.model.SelectedListType
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent

data class KitapListState(
    val selectedListType: SelectedListType = SelectedListType.TUM_LISTE,
    val kitapServiceListeSource: BaseResourceEvent<List<KitapListeItem>> = BaseResourceEvent.Loading()
)
