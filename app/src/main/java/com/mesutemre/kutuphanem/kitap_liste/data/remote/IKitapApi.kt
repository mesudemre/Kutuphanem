package com.mesutemre.kutuphanem.kitap_liste.data.remote

import com.mesutemre.kutuphanem.kitap_liste.data.remote.dto.KitapDto
import com.mesutemre.kutuphanem.model.ResponseStatusModel
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface IKitapApi {

    @POST("api/kitap/liste")
    suspend fun getKitapListe(@Body kitap: KitapDto): Response<List<KitapDto>>

    @Streaming
    @GET
    suspend fun downloadKitapResim(@Url imageUrl: String): Response<ResponseBody>

    @POST("api/kitap/begen")
    suspend fun kitapBegen(@Body kitap: KitapDto): Response<ResponseStatusModel>
}