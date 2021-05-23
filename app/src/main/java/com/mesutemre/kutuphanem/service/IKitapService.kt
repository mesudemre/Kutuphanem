package com.mesutemre.kutuphanem.service

import com.mesutemre.kutuphanem.model.KitapModel
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface IKitapService {

    @Headers("Content-Type: application/json")
    @POST("api/kitap/liste")
    fun getKitapListe(@Body jsonStr: String): Single<ArrayList<KitapModel>>;
}