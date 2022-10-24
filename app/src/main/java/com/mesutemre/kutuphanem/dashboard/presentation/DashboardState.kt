package com.mesutemre.kutuphanem.dashboard.presentation

import com.mesutemre.kutuphanem.dashboard.domain.model.*
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent

data class DashboardState(
    val introductionList:List<IntroductionPagerData> = emptyList(),
    val kullaniciBilgi: BaseResourceEvent<DashboardKullaniciBilgiData> = BaseResourceEvent.Loading(),
    val kategoriListeResource:BaseResourceEvent<List<DashboardKategoriItem>> = BaseResourceEvent.Loading(),
    val kitapTurIstatistikResource: BaseResourceEvent<List<DashboardKitapTurIstatistikItem>> = BaseResourceEvent.Loading(),
    val kitapYilIstatistikResource: BaseResourceEvent<List<DashBoardKitapYilIstatistikItem>> = BaseResourceEvent.Loading()
)
