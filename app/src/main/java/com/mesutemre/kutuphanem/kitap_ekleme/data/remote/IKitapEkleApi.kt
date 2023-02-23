package com.mesutemre.kutuphanem.kitap_ekleme.data.remote

import com.mesutemre.kutuphanem.kitap_liste.data.remote.dto.KitapDto
import com.mesutemre.kutuphanem.model.ResponseStatusModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface IKitapEkleApi {

    @POST("api/kitap/kaydet")
    suspend fun kitapKaydet(@Body kitap: KitapDto): Response<ResponseStatusModel>

    @Multipart
    @POST("api/kitap/resim/yukle")
    suspend fun kitapResimYukle(
        @Part file: MultipartBody.Part,
        @Part("kitapId") kitapId: RequestBody
    ): Response<ResponseStatusModel>
}