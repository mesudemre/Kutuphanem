package com.mesutemre.kutuphanem.service

import com.mesutemre.kutuphanem.model.AccountCredentials
import com.mesutemre.kutuphanem.model.Kullanici
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface KullaniciService {

    @POST("login")
    fun login(@Body user:AccountCredentials):Single<String>;

    @GET("api/kullanici/bilgi")
    fun getKullaniciBilgi():Single<Kullanici>;

}