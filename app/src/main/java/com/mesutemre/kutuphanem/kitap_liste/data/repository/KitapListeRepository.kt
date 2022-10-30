package com.mesutemre.kutuphanem.kitap_liste.data.repository

import com.mesutemre.kutuphanem.kitap_liste.data.remote.dto.KitapDto
import retrofit2.Response

interface KitapListeRepository {

    suspend fun getKitapListe(kitapDto: KitapDto): Response<List<KitapDto>>
}