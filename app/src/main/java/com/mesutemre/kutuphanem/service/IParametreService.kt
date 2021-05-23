package com.mesutemre.kutuphanem.service

import com.mesutemre.kutuphanem.model.KitapturModel
import com.mesutemre.kutuphanem.model.ResponseStatusModel
import com.mesutemre.kutuphanem.model.YayineviModel
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface IParametreService {

    @GET("api/parametre/yayinevi/liste")
    fun getYayinEviListe(): Single<ArrayList<YayineviModel>>;

    @Headers("Content-Type: application/json")
    @POST("api/parametre/yayinevi/kaydet")
    fun yayinEviKaydet(@Body jsonStr: String):Single<ResponseStatusModel>;

    @GET("api/parametre/kitaptur/liste")
    fun getKitapTurListe():Single<ArrayList<KitapturModel>>;

    @Headers("Content-Type: application/json")
    @POST("api/parametre/kitaptur/kaydet")
    fun kitapTurKaydet(@Body jsonStr: String):Single<ResponseStatusModel>;
}