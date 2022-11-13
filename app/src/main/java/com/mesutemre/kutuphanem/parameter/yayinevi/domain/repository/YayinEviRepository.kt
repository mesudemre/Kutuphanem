package com.mesutemre.kutuphanem.parameter.yayinevi.domain.repository

import com.mesutemre.kutuphanem.model.ResponseStatusModel
import com.mesutemre.kutuphanem.parameter.yayinevi.data.dao.entity.YayinEviEntity
import com.mesutemre.kutuphanem.parameter.yayinevi.data.remote.dto.YayinEviDto
import retrofit2.Response

interface YayinEviRepository {

    suspend fun getYayinEviLisetByAPI(): Response<List<YayinEviDto>>

    suspend fun getYayinEviListeByDAO(): List<YayinEviEntity>

    suspend fun saveYayinEviList(vararg yayinEvi: YayinEviEntity)

    suspend fun deleteYayinEviList()

    suspend fun yayinEviKaydet(yayinEviDto: YayinEviDto): Response<ResponseStatusModel>

    suspend fun checkYayinEviDbKayit(key: String): Boolean

    suspend fun saveYayinEviDbKayitToDataStore(key: String, value: Boolean)

    suspend fun clearYayinEviDbKayitCache(key: String)
}