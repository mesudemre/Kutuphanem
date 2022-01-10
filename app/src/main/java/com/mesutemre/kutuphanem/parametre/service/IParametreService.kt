package com.mesutemre.kutuphanem.parametre.service

import com.mesutemre.kutuphanem.parametre.kitaptur.model.KitapturModel
import com.mesutemre.kutuphanem.model.ResponseStatusModel
import com.mesutemre.kutuphanem.parametre.yayinevi.model.YayineviModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface IParametreService {

    @GET("api/parametre/yayinevi/liste")
    suspend fun getYayinEviListe(): Response<List<YayineviModel>>;

    @Headers("Content-Type: application/json")
    @POST("api/parametre/yayinevi/kaydet")
    suspend fun yayinEviKaydet(@Body jsonStr: String):Response<ResponseStatusModel>;

    @Headers("Content-Type: application/json")
    @POST("api/parametre/yayinevi/kaydet")
    suspend fun yayinEviKaydetGeneric(@Body jsonStr: String):Response<ResponseStatusModel>;

    @GET("api/parametre/kitaptur/liste")
    suspend fun getKitapTurListe():Response<List<KitapturModel>>;

    @Headers("Content-Type: application/json")
    @POST("api/parametre/kitaptur/kaydet")
    suspend fun kitapTurKaydet(@Body jsonStr: String):Response<ResponseStatusModel>;
}