package com.mesutemre.kutuphanem.parameter.kitaptur.data.remote.dto

import com.mesutemre.kutuphanem.model.ResponseStatusModel
import com.mesutemre.kutuphanem.parameter.yayinevi.data.remote.dto.YayinEviDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface IKitapTurApi {

    @GET("api/parametre/kitaptur/liste")
    suspend fun getKitapTurListe(): Response<List<YayinEviDto>>

    @POST("api/parametre/kitaptur/kaydet")
    suspend fun kitapTurKaydet(@Body kitapTurDto: KitapTurDto): Response<ResponseStatusModel>

}