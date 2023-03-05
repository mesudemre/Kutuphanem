package com.mesutemre.kutuphanem.profile.domain.use_case

import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem.profile.data.repository.KullaniciRepository
import com.mesutemre.kutuphanem.profile.domain.model.KullaniciBilgiModel
import com.mesutemre.kutuphanem.util.KULLANICI_DB_MEVCUT
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.invoke
import javax.inject.Inject

class SaveKullaniciBilgiUseCase @Inject constructor(
    private val saveKullaniciBilgiIntoDbUseCase: SaveKullaniciBilgiIntoDbUseCase,
    private val saveKullaniciCinsiyetIntoDbUseCase: SaveKullaniciCinsiyetIntoDbUseCase,
    private val saveKullaniciIlgiAlanIntoDbUseCase: SaveKullaniciIlgiAlanIntoDbUseCase,
    private val kullaniciRepository: KullaniciRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(
        kullaniciBilgi: KullaniciBilgiModel
    ) {
        ioDispatcher.invoke {
            async {
                saveKullaniciBilgiIntoDbUseCase(kullaniciBilgi)
            }.invokeOnCompletion {
                async {
                    saveKullaniciCinsiyetIntoDbUseCase(
                        cinsiyetModel = kullaniciBilgi.cinsiyet,
                        kullaniciAd = kullaniciBilgi.kullaniciAdi
                    )
                }
                async {
                    saveKullaniciIlgiAlanIntoDbUseCase(
                        ilgiAlanList = kullaniciBilgi.ilgiAlanList,
                        kullaniciAd = kullaniciBilgi.kullaniciAdi
                    )
                }
            }

        }.also {
            kullaniciRepository.saveKullaniciMevcutInDb(KULLANICI_DB_MEVCUT)
        }
    }
}