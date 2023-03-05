package com.mesutemre.kutuphanem.profile.domain.use_case

import com.google.gson.Gson
import com.mesutemre.kutuphanem.dashboard.domain.model.DashboardKullaniciBilgiData
import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem.profile.data.dao.entity.convertToKullaniciCinsiyet
import com.mesutemre.kutuphanem.profile.data.dao.entity.convertToKullaniciIlgiAlanModel
import com.mesutemre.kutuphanem.profile.data.remote.dto.CinsiyetDto
import com.mesutemre.kutuphanem.profile.data.remote.dto.toKullaniciBilgiModel
import com.mesutemre.kutuphanem.profile.data.repository.KullaniciRepository
import com.mesutemre.kutuphanem.profile.domain.model.KullaniciBilgiModel
import com.mesutemre.kutuphanem.util.KULLANICI_DB_MEVCUT
import com.mesutemre.kutuphanem.util.convertDate2String
import com.mesutemre.kutuphanem.util.convertRersourceEventType
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import com.mesutemre.kutuphanem_base.use_case.DbCallUseCase
import com.mesutemre.kutuphanem_base.use_case.IDbCall
import com.mesutemre.kutuphanem_base.use_case.IServiceCall
import com.mesutemre.kutuphanem_base.use_case.ServiceCallUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetKullaniciBilgi @Inject constructor(
    private val kullaniciRepository: KullaniciRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val saveKullaniciIntoDb: SaveKullaniciBilgiUseCase
) : IServiceCall by ServiceCallUseCase(), IDbCall by DbCallUseCase() {

    suspend operator fun invoke(): Flow<BaseResourceEvent<KullaniciBilgiModel>> {
        val isKullaniciDb = kullaniciRepository.checkKullaniciDbMevcut(KULLANICI_DB_MEVCUT)
        if (isKullaniciDb.not()) {
            val kullaniciBilgi = serviceCall {
                kullaniciRepository.getKullaniciBilgiByAPI()
            }.map {
                it.convertRersourceEventType {
                    it.data?.toKullaniciBilgiModel()
                }
            }.flowOn(ioDispatcher)
            kullaniciBilgi.collectLatest {
                if (it is BaseResourceEvent.Success) {
                    saveKullaniciIntoDb(it.data!!)
                }
            }
            return kullaniciBilgi as Flow<BaseResourceEvent<KullaniciBilgiModel>>
        } else {
            val kullanici = Gson().fromJson(
                kullaniciRepository.getKullaniciFromDataStore(
                    "TEMP_USER_INFO"
                ),
                DashboardKullaniciBilgiData::class.java
            )
            return dbCall {
                kullaniciRepository.getKullaniciBilgiByKullaniciAdi(
                    kullanici.kullaniciAd
                )
            }.map {
                it.convertRersourceEventType {
                    val kullaniciBilgi = it.data?.keys?.first()?.kullaniciBilgi
                    val kullaniciIlgiAlan = it.data?.values?.first()?.map {
                        it.convertToKullaniciIlgiAlanModel()
                    }
                    KullaniciBilgiModel(
                        ad = kullaniciBilgi?.ad ?: "",
                        soyad = kullaniciBilgi?.soyad ?: "",
                        ilgiAlanList = kullaniciIlgiAlan ?: emptyList(),
                        adSoyad = kullaniciBilgi?.ad + " " + kullaniciBilgi?.soyad,
                        eposta = kullaniciBilgi?.eposta ?: "",
                        cinsiyet = it.data?.keys?.first()?.cinsiyet?.convertToKullaniciCinsiyet()
                            ?: CinsiyetDto(
                                label = "",
                                value = ""
                            ),
                        dogumTarihi = kullaniciBilgi?.dogumTarihi?.convertDate2String() ?: "",
                        isHaberdar = kullaniciBilgi?.isHaberdar ?: false,
                        resim = kullaniciBilgi?.kullaniciResim ?: "",
                        kullaniciAdi = kullanici.kullaniciAd
                    )
                }
            }.flowOn(ioDispatcher)
        }
    }
}