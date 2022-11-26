package com.mesutemre.kutuphanem.kitap_detay.presentation

import com.mesutemre.kutuphanem.kitap_detay.domain.model.KitapDetayItem
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent

data class KitapDetayScreenState(
    val kitapId: Int = 0,
    val isFromArsiv: Boolean = false,
    val kitapDetayItemResource: BaseResourceEvent<KitapDetayItem> = BaseResourceEvent.Loading()
)
