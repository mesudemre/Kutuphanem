package com.mesutemre.kutuphanem

import com.mesutemre.kutuphanem_base.model.BaseResourceEvent

data class MainActivityState(
    val tokenResourceEvent: BaseResourceEvent<String> = BaseResourceEvent.Loading(),
    val splashLoadingState: Boolean = true
)
