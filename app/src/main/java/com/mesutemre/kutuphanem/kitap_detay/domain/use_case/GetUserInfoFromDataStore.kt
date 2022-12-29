package com.mesutemre.kutuphanem.kitap_detay.domain.use_case

import com.mesutemre.kutuphanem.dashboard.domain.model.DashboardKullaniciBilgiData
import com.mesutemre.kutuphanem.kitap_detay.data.repository.KitapDetayRepository
import javax.inject.Inject

class GetUserInfoFromDataStore @Inject constructor(
    private val kitapDetayRepository: KitapDetayRepository
) {

    suspend operator fun invoke(): DashboardKullaniciBilgiData {
        return kitapDetayRepository.getDashboardUserInfo()
    }
}