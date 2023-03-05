package com.mesutemre.kutuphanem.profile.domain.use_case

import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem.profile.data.repository.KullaniciRepository
import com.mesutemre.kutuphanem.profile.domain.model.KullaniciIlgiAlanModel
import com.mesutemre.kutuphanem.profile.domain.model.toKullaniciIlgiAlanEntity
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import com.mesutemre.kutuphanem_base.use_case.DbCallUseCase
import com.mesutemre.kutuphanem_base.use_case.IDbCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SaveKullaniciIlgiAlanIntoDbUseCase @Inject constructor(
    private val kullaniciRepository: KullaniciRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : IDbCall by DbCallUseCase() {

    operator fun invoke(
        ilgiAlanList: List<KullaniciIlgiAlanModel>,
        kullaniciAd: String
    ): Flow<BaseResourceEvent<Unit>> {
        return dbCall {
            kullaniciRepository.saveKullaniciIlgiAlanlar(
                *ilgiAlanList.map {
                    it.toKullaniciIlgiAlanEntity(kullaniciAd)
                }.toTypedArray()
            )
        }.flowOn(ioDispatcher)
    }
}