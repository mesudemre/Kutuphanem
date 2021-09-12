package com.mesutemre.kutuphanem.service

import com.mesutemre.kutuphanem.model.KitapModel
import com.mesutemre.kutuphanem.model.ResponseStatusModel
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
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

    @Headers("Content-Type: application/json")
    @POST("api/kitap/begen")
    fun kitapBegen(@Body jsonStr:String):Single<ResponseStatusModel>;

    @Headers("Content-Type: application/json")
    @POST("api/kitap/begen/kaldir")
    fun kitapBegenKaldir(@Body jsonStr:String):Single<ResponseStatusModel>;

    @Headers("Content-Type: application/json")
    @POST("api/kitap/nesne")
    fun getKitapDetay(@Body jsonStr:String):Single<KitapModel>;

    @Headers("Content-Type: application/json")
    @POST("api/kitap/begen/liste")
    suspend fun getBegenilenKitapListe(@Body jsonStr:String):Response<List<KitapModel>>;
}