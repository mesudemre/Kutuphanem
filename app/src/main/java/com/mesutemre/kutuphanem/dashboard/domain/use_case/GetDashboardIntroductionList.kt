package com.mesutemre.kutuphanem.dashboard.domain.use_case

import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.base.BaseUseCase
import com.mesutemre.kutuphanem.dashboard.domain.model.IntroductionPagerData
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 28.08.2022
 */
class GetDashboardIntroductionList @Inject constructor() : BaseUseCase() {

    operator fun invoke(): List<IntroductionPagerData> = listOf(
        IntroductionPagerData(description = R.string.tanitimText1),
        IntroductionPagerData(description = R.string.tanitimText2),
        IntroductionPagerData(description = R.string.tanitimText3)
    )
}