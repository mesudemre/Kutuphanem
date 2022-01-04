package com.mesutemre.kutuphanem.auth.service

import com.mesutemre.kutuphanem.auth.login.model.AccountCredentials
import com.mesutemre.kutuphanem.auth.profil.model.Kullanici
import com.mesutemre.kutuphanem.model.ResponseStatusModel
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface KullaniciService {

    @POST("login")
    suspend fun login(@Body user: AccountCredentials):Response<String>;

    @GET("api/kullanici/bilgi")
    suspend fun getKullaniciBilgi():Response<Kullanici>;

    @Headers("Content-Type: application/json")
    @POST("api/kullanici/guncelle")
    fun kullaniciBilgiGuncelle(@Body jsonStr:String):Single<ResponseStatusModel>;

    @Multipart
    @POST("api/kullanici/resim/yukle")
    fun kullaniciResimGuncelle(@Part file: MultipartBody.Part, @Part("username") username: RequestBody):Single<ResponseStatusModel>;
}