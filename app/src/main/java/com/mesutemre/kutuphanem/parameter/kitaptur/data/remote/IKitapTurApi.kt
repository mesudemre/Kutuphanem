package com.mesutemre.kutuphanem.parameter.kitaptur.data.remote.dto

import com.mesutemre.kutuphanem.model.ResponseStatusModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface IKitapTurApi {

    @GET("api/parametre/kitaptur/liste")
    suspend fun getKitapTurListe(): Response<List<KitapTurDto>>

    @POST("api/parametre/kitaptur/kaydet")
    suspend fun kitapTurKaydet(@Body kitapTurDto: KitapTurDto): Response<ResponseStatusModel>

}