package com.mesutemre.kutuphanem.profile.data.remote

import com.mesutemre.kutuphanem.profile.data.remote.dto.KullaniciDto
import retrofit2.Response
import retrofit2.http.GET

interface IKullaniciApi {

    @GET("api/kullanici/bilgi")
    suspend fun getKullaniciBilgi(): Response<KullaniciDto>
}