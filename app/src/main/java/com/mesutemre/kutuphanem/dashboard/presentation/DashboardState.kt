package com.mesutemre.kutuphanem.dashboard.presentation

import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.dashboard.domain.model.DashboardKategoriItem
import com.mesutemre.kutuphanem.dashboard.domain.model.DashboardKitapTurIstatistikItem
import com.mesutemre.kutuphanem.dashboard.domain.model.DashboardKullaniciBilgiData
import com.mesutemre.kutuphanem.dashboard.domain.model.IntroductionPagerData

data class DashboardState(
    val introductionList:List<IntroductionPagerData> = emptyList(),
    val kullaniciBilgi: BaseResourceEvent<DashboardKullaniciBilgiData> = BaseResourceEvent.Loading(),
    val kategoriListeResource:BaseResourceEvent<List<DashboardKategoriItem>> = BaseResourceEvent.Loading(),
    val kitapTurIstatistikResource: BaseResourceEvent<List<DashboardKitapTurIstatistikItem>> = BaseResourceEvent.Loading()
)