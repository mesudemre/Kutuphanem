package com.mesutemre.kutuphanem.profile.domain.use_case

import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem.profile.data.dao.entity.KullaniciBilgiEntity
import com.mesutemre.kutuphanem.profile.data.repository.KullaniciRepository
import com.mesutemre.kutuphanem.profile.domain.model.KullaniciBilgiModel
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import com.mesutemre.kutuphanem_base.use_case.DbCallUseCase
import com.mesutemre.kutuphanem_base.use_case.IDbCall
import com.mesutemre.kutuphanem_base.util.convertStringToDate
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SaveKullaniciBilgiIntoDbUseCase @Inject constructor(
    private val kullaniciRepository: KullaniciRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : IDbCall by DbCallUseCase() {

    operator fun invoke(kullaniciBilgiModel: KullaniciBilgiModel): Flow<BaseResourceEvent<Unit>> {
        return dbCall {
            kullaniciRepository.saveKullaniciBilgi(
                *listOf(
                    KullaniciBilgiEntity(
                        kullaniciAd = kullaniciBilgiModel.kullaniciAdi,
                        ad = kullaniciBilgiModel.ad,
                        soyad = kullaniciBilgiModel.soyad,
                        dogumTarihi = kullaniciBilgiModel.dogumTarihi.convertStringToDate(),
                        kullaniciResim = kullaniciBilgiModel.resim,
                        eposta = kullaniciBilgiModel.eposta,
                        isHaberdar = kullaniciBilgiModel.isHaberdar
                    )
                ).toTypedArray()
            )
        }.flowOn(ioDispatcher)
    }
}