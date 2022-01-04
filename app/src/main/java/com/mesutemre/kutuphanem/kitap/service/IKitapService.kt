package com.mesutemre.kutuphanem.kitap.service

import com.mesutemre.kutuphanem.kitap.liste.model.KitapModel
import com.mesutemre.kutuphanem.model.ResponseStatusModel
import com.mesutemre.kutuphanem.kitap.yorum.model.YorumListeModel
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface IKitapService {

    @Headers("Content-Type: application/json")
    @POST("api/kitap/liste")
    suspend fun getTumKitapListe(@Body jsonStr:String):Response<List<KitapModel>>;

    @Headers("Content-Type: application/json")
    @POST("api/kitap/kaydet")
    suspend fun kitapKaydet(@Body jsonStr:String):Response<ResponseStatusModel>;

    @Multipart
    @POST("api/kitap/resim/yukle")
    suspend fun kitapResimYukle(@Part file: MultipartBody.Part, @Part("kitapId") kitapId: RequestBody):Response<ResponseStatusModel>;

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

    @Headers("Content-Type: application/json")
    @POST("api/kitap/yorum/kaydet")
    fun kitapYorumKaydet(@Body jsonStr:String):Single<ResponseStatusModel>;

    @Headers("Content-Type: application/json")
    @GET("api/kitap/yorumlar/{kitapId}")
    fun getKitapYorumListe(@Path("kitapId") kitapId:Int):Single<YorumListeModel>;

    @Headers("Content-Type: application/json")
    @POST("api/kitap/puan/kaydet")
    fun kitapPuanKaydet(@Body jsonStr: String):Single<ResponseStatusModel>;
}