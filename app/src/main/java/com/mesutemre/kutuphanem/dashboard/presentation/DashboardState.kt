package com.mesutemre.kutuphanem.dashboard.presentation

import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.dashboard.domain.model.DashboardKullaniciBilgiData
import com.mesutemre.kutuphanem.dashboard.domain.model.IntroductionPagerData

data class DashboardState(
    val introductionList:List<IntroductionPagerData> = emptyList(),
    val kullaniciBilgi: BaseResourceEvent<DashboardKullaniciBilgiData> = BaseResourceEvent.Nothing()
)
