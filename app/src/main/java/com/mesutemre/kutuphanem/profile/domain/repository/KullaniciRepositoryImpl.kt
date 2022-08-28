package com.mesutemre.kutuphanem.profile.domain.repository

import com.mesutemre.kutuphanem.profile.data.remote.IKullaniciApi
import com.mesutemre.kutuphanem.profile.data.remote.dto.KullaniciDto
import com.mesutemre.kutuphanem.profile.data.repository.KullaniciRepository
import retrofit2.Response
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 28.08.2022
 */
class KullaniciRepositoryImpl @Inject constructor(
    private val service: IKullaniciApi
): KullaniciRepository {

    override suspend fun getKullaniciBilgiByAPI(): Response<KullaniciDto> {
        return service.getKullaniciBilgi()
    }

}