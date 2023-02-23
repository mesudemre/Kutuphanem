package com.mesutemre.kutuphanem.kitap_ekleme.data.repository

import com.mesutemre.kutuphanem.kitap_liste.data.remote.dto.KitapDto
import com.mesutemre.kutuphanem.model.ResponseStatusModel
import retrofit2.Response
import java.io.File

interface KitapEklemeRepository {

    suspend fun kitapKaydet(kitap: KitapDto): Response<ResponseStatusModel>

    suspend fun kitapResimYukle(
        kitapResim: File,
        kitapId: String
    ): Response<ResponseStatusModel>
}