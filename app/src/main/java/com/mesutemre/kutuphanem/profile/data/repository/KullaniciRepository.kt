package com.mesutemre.kutuphanem.profile.data.repository

import com.mesutemre.kutuphanem.profile.data.remote.dto.KullaniciDto
import retrofit2.Response

interface KullaniciRepository {

    suspend fun getKullaniciBilgiByAPI(): Response<KullaniciDto>
}