package com.mesutemre.kutuphanem.kitap_ekleme.domain.repository

import com.mesutemre.kutuphanem.kitap_ekleme.data.remote.IKitapEkleApi
import com.mesutemre.kutuphanem.kitap_ekleme.data.repository.KitapEklemeRepository
import com.mesutemre.kutuphanem.kitap_liste.data.remote.dto.KitapDto
import com.mesutemre.kutuphanem.model.ResponseStatusModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject

class KitapEklemeRepositoryImpl @Inject constructor(
    private val api: IKitapEkleApi
) : KitapEklemeRepository {

    override suspend fun kitapKaydet(kitap: KitapDto): Response<ResponseStatusModel> {
        return api.kitapKaydet(kitap)
    }

    override suspend fun kitapResimYukle(
        kitapResim: File,
        kitapId: String
    ): Response<ResponseStatusModel> {
        val kitapIdParam: RequestBody = kitapId.toRequestBody("text/plain".toMediaTypeOrNull())
        val fileParam: RequestBody =
            kitapResim.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val file: MultipartBody.Part =
            MultipartBody.Part.createFormData("file", kitapResim.name, fileParam)
        return api.kitapResimYukle(file, kitapIdParam)
    }
}