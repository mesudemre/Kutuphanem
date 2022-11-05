package com.mesutemre.kutuphanem.kitap_liste.domain.repository

import com.mesutemre.kutuphanem.kitap_liste.data.remote.IKitapApi
import com.mesutemre.kutuphanem.kitap_liste.data.remote.dto.KitapDto
import com.mesutemre.kutuphanem.kitap_liste.data.repository.KitapListeRepository
import retrofit2.Response
import javax.inject.Inject

class KitapListeRepositoryImpl @Inject constructor(
    private val api: IKitapApi
) : KitapListeRepository {

    override suspend fun getKitapListe(kitapDto: KitapDto): Response<List<KitapDto>> {
        return api.getKitapListe(kitapDto)
    }
}