package com.mesutemre.kutuphanem.parameter.yayinevi.data.remote.dto

import com.mesutemre.kutuphanem.model.ResponseStatusModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface IYayinEviApi {

    @GET("api/parametre/yayinevi/liste")
    suspend fun getYayinEviListe(): Response<List<YayinEviDto>>

    @POST("api/parametre/yayinevi/kaydet")
    suspend fun yayinEviKaydet(@Body yayinEviDto: YayinEviDto):Response<ResponseStatusModel>

}