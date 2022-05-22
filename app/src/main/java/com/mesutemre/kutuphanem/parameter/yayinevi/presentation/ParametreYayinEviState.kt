package com.mesutemre.kutuphanem.parameter.yayinevi.presentation

import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.parameter.yayinevi.domain.model.YayinEviItem

data class ParametreYayinEviState(
    val yayinEviFilterText: String = "",
    val yayinEviList: BaseResourceEvent<List<YayinEviItem>> = BaseResourceEvent.Nothing(),
    val swipeRefreshing: Boolean = false
)
