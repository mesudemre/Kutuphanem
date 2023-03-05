package com.mesutemre.kutuphanem.profile.domain.use_case

import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem.profile.data.dao.entity.KullaniciCinsiyetEntity
import com.mesutemre.kutuphanem.profile.data.remote.dto.CinsiyetDto
import com.mesutemre.kutuphanem.profile.data.repository.KullaniciRepository
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import com.mesutemre.kutuphanem_base.use_case.DbCallUseCase
import com.mesutemre.kutuphanem_base.use_case.IDbCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SaveKullaniciCinsiyetIntoDbUseCase @Inject constructor(
    private val kullaniciRepository: KullaniciRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : IDbCall by DbCallUseCase() {

    operator fun invoke(
        cinsiyetModel: CinsiyetDto,
        kullaniciAd: String
    ): Flow<BaseResourceEvent<Unit>> {
        return dbCall {
            kullaniciRepository.saveKullaniciCinsiyetBilgi(
                * listOf(
                    KullaniciCinsiyetEntity(
                        kullaniciAd = kullaniciAd,
                        cinsiyetLabel = cinsiyetModel.label,
                        cinsiyetValue = cinsiyetModel.value
                    )
                ).toTypedArray()
            )
        }.flowOn(ioDispatcher)
    }
}