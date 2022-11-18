package com.mesutemre.kutuphanem.kitap_liste.data.repository

import com.mesutemre.kutuphanem.kitap_liste.data.dao.entity.KitapEntity
import com.mesutemre.kutuphanem.kitap_liste.data.remote.dto.KitapDto
import com.mesutemre.kutuphanem.kitap_liste.domain.model.KitapArsivItem
import com.mesutemre.kutuphanem.model.ResponseStatusModel
import okhttp3.ResponseBody
import retrofit2.Response

interface KitapListeRepository {

    suspend fun getKitapListe(kitapDto: KitapDto): Response<List<KitapDto>>

    suspend fun kitapArsivKaydet(kitapEntity: KitapEntity)

    suspend fun getKitapArsivListe(): List<KitapArsivItem>

    suspend fun deleteKitapById(kitapId: Int)

    suspend fun downloadKitapResim(imageUrl: String): Response<ResponseBody>

    suspend fun kitapBegen(kitapDto: KitapDto): Response<ResponseStatusModel>
}