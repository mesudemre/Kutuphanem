package com.mesutemre.kutuphanem.kitap_detay.data.remote

import com.mesutemre.kutuphanem.kitap_detay.data.remote.dto.YorumListeDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface IKitapYorumApi {

    @GET("api/kitap/yorumlar/{kitapId}")
    suspend fun getKitapYorumListe(@Path("kitapId") kitapId: Int): Response<YorumListeDto>
}