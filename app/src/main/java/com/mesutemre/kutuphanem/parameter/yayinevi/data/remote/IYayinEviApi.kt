package com.mesutemre.kutuphanem.parameter.yayinevi.data.remote.dto

import retrofit2.Response
import retrofit2.http.GET

interface IYayinEviApi {

    @GET("api/parametre/yayinevi/liste")
    suspend fun getYayinEviListe(): Response<List<YayinEviDto>>

}