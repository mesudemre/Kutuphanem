package com.mesutemre.kutuphanem.dashboard.data.remote

import com.mesutemre.kutuphanem.dashboard.data.remote.dto.KitapTurIstatistikDto
import com.mesutemre.kutuphanem.dashboard.data.remote.dto.KitapYilIstatistikDto
import retrofit2.Response
import retrofit2.http.GET

interface IDashBoardApi {

    @GET("api/kitap/tur/istatistik")
    suspend fun getKitapTurIstatistikListe(): Response<List<KitapTurIstatistikDto>>

    @GET("api/kitap/yil/istatistik")
    suspend fun getKitapYilIstatistikListe(): Response<List<KitapYilIstatistikDto>>
}