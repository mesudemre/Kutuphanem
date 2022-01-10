package com.mesutemre.kutuphanem.kitap.service

import com.mesutemre.kutuphanem.kitap.liste.model.KitapModel
import com.mesutemre.kutuphanem.model.ResponseStatusModel
import com.mesutemre.kutuphanem.kitap.yorum.model.YorumListeModel
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
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
    suspend fun kitapBegen(@Body jsonStr:String):Response<ResponseStatusModel>;

    @Headers("Content-Type: application/json")
    @POST("api/kitap/begen/kaldir")
    suspend fun kitapBegenKaldir(@Body jsonStr:String):Response<ResponseStatusModel>;

    @Headers("Content-Type: application/json")
    @POST("api/kitap/nesne")
    suspend fun getKitapDetay(@Body jsonStr:String):Response<KitapModel>;

    @Headers("Content-Type: application/json")
    @POST("api/kitap/begen/liste")
    suspend fun getBegenilenKitapListe(@Body jsonStr:String):Response<List<KitapModel>>;

    @Headers("Content-Type: application/json")
    @POST("api/kitap/yorum/kaydet")
    suspend fun kitapYorumKaydet(@Body jsonStr:String):Response<ResponseStatusModel>;

    @Headers("Content-Type: application/json")
    @GET("api/kitap/yorumlar/{kitapId}")
    suspend fun getKitapYorumListe(@Path("kitapId") kitapId:Int):Response<YorumListeModel>;

    @Headers("Content-Type: application/json")
    @POST("api/kitap/puan/kaydet")
    suspend fun kitapPuanKaydet(@Body jsonStr: String):Response<ResponseStatusModel>;

    @Streaming
    @GET
    suspend fun downloadKitapResim(@Url fileUrl:String):Response<ResponseBody>;
}