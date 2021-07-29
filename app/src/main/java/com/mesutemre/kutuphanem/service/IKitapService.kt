package com.mesutemre.kutuphanem.service

import com.mesutemre.kutuphanem.model.KitapModel
import com.mesutemre.kutuphanem.model.ResponseStatusModel
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface IKitapService {

    @Headers("Content-Type: application/json")
    @POST("api/kitap/liste")
    fun getKitapListe(@Body jsonStr: String): Single<ArrayList<KitapModel>>;

    @Headers("Content-Type: application/json")
    @POST("api/kitap/kaydet")
    fun kitapKaydet(@Body jsonStr:String):Single<ResponseStatusModel>;

    @Multipart
    @POST("api/kitap/resim/yukle")
    fun kitapResimYukle(@Part file: MultipartBody.Part, @Part("kitapId") kitapId: RequestBody):Single<ResponseStatusModel>;
}