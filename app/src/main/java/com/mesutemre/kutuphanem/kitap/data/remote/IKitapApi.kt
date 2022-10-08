package com.mesutemre.kutuphanem.kitap.data.remote

import com.mesutemre.kutuphanem.kitap.data.remote.dto.KitapDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface IKitapApi {

    @POST("api/kitap/liste")
    suspend fun getKitapListe(@Body kitap: KitapDto): Response<List<KitapDto>>
}