package com.mesutemre.kutuphanem.kitap_detay.domain.repository

import com.mesutemre.kutuphanem.kitap_detay.data.dao.IKitapDetayDao
import com.mesutemre.kutuphanem.kitap_detay.data.dao.entity.KitapEntityWithYayinEviKitapTur
import com.mesutemre.kutuphanem.kitap_detay.data.repository.KitapDetayRepository
import com.mesutemre.kutuphanem.kitap_liste.data.remote.IKitapApi
import com.mesutemre.kutuphanem.kitap_liste.data.remote.dto.KitapDto
import retrofit2.Response
import javax.inject.Inject

class KitapDetayRepositoryImpl @Inject constructor(
    private val api: IKitapApi,
    private val dao: IKitapDetayDao
) : KitapDetayRepository {

    override suspend fun getKitapFromAPIById(kitapId: Int): Response<List<KitapDto>> {
        return api.getKitapListe(
            KitapDto(
                id = kitapId
            )
        )
    }

    override suspend fun getKitapFromDbById(kitapId: Int): KitapEntityWithYayinEviKitapTur {
        return dao.getKitapById(kitapId)
    }
}