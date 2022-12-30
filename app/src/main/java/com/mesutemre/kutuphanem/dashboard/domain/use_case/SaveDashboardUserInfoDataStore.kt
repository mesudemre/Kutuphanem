package com.mesutemre.kutuphanem.dashboard.domain.use_case

import com.mesutemre.kutuphanem.dashboard.data.repository.DashBoardRepository
import com.mesutemre.kutuphanem.dashboard.domain.model.DashboardKullaniciBilgiData
import javax.inject.Inject

class SaveDashboardUserInfoDataStore @Inject constructor(
    private val repository: DashBoardRepository
) {
    suspend operator fun invoke(data: DashboardKullaniciBilgiData) {
        repository.saveDashboardUserInfoToDataStore(data)
    }
}