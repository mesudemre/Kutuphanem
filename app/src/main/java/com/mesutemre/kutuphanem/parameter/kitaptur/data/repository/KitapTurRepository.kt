package com.mesutemre.kutuphanem.parameter.kitaptur.data.repository

import com.mesutemre.kutuphanem.model.ResponseStatusModel
import com.mesutemre.kutuphanem.parameter.kitaptur.data.dao.entity.KitapTurEntity
import com.mesutemre.kutuphanem.parameter.kitaptur.data.remote.dto.KitapTurDto
import retrofit2.Response

interface KitapTurRepository {

    suspend fun getKitapTurListeByAPI(): Response<List<KitapTurDto>>

    suspend fun getKitapTurListeByDAO(): List<KitapTurEntity>

    suspend fun saveKitapTurList(vararg kitapTur: KitapTurEntity)

    suspend fun deleteKitapTurList()

    suspend fun kitapTurKaydet(kitapTurDto: KitapTurDto): Response<ResponseStatusModel>

}