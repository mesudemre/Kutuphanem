package com.mesutemre.kutuphanem.parameter.yayinevi.presentation

import com.mesutemre.kutuphanem.model.ResponseStatusModel
import com.mesutemre.kutuphanem.parameter.yayinevi.domain.model.YayinEviItem
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent

data class ParametreYayinEviState(
    val yayinEviFilterText: String = "",
    val yayinEviList: BaseResourceEvent<List<YayinEviItem>> = BaseResourceEvent.Nothing(),
    val swipeRefreshing: Boolean = false,
    val defaultYayineviList: List<YayinEviItem>? = null,
    val isPopUpShow: Boolean = false,
    val selectedYayinevi: YayinEviItem? = null,
    val yayinEviDelete: BaseResourceEvent<ResponseStatusModel?> = BaseResourceEvent.Nothing()
)
