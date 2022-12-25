package com.mesutemre.kutuphanem.kitap_detay.data.repository

import com.mesutemre.kutuphanem.kitap_detay.data.dao.entity.KitapEntityWithYayinEviKitapTur
import com.mesutemre.kutuphanem.kitap_detay.data.remote.dto.YorumListeDto
import com.mesutemre.kutuphanem.kitap_liste.data.remote.dto.KitapDto
import retrofit2.Response

interface KitapDetayRepository {

    suspend fun getKitapFromAPIById(kitapId: Int): Response<List<KitapDto>>

    suspend fun getKitapFromDbById(kitapId: Int): KitapEntityWithYayinEviKitapTur

    suspend fun getKitapYorumListeByKitapId(kitapId: Int): Response<YorumListeDto>
}