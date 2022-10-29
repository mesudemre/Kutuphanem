package com.mesutemre.kutuphanem.parameter.kitaptur.presentation

import com.mesutemre.kutuphanem.model.ResponseStatusModel
import com.mesutemre.kutuphanem.parameter.kitaptur.domain.model.KitapTurItem
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent

data class ParametreKitapTurState(
    val kitapTurFilterText: String = "",
    val kitapTurList: BaseResourceEvent<List<KitapTurItem>> = BaseResourceEvent.Nothing(),
    val swipeRefreshing: Boolean = false,
    val defaultKitapTurList: List<KitapTurItem>? = null,
    val isPopUpShow: Boolean = false,
    val selectedKitapTur: KitapTurItem? = null,
    val kitapTurDelete: BaseResourceEvent<ResponseStatusModel?> = BaseResourceEvent.Nothing()
)
