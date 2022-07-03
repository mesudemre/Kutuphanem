package com.mesutemre.kutuphanem.parameter.kitaptur.domain.repository

import com.mesutemre.kutuphanem.model.ResponseStatusModel
import com.mesutemre.kutuphanem.parameter.kitaptur.data.dao.IKitapTurDao
import com.mesutemre.kutuphanem.parameter.kitaptur.data.dao.entity.KitapTurEntity
import com.mesutemre.kutuphanem.parameter.kitaptur.data.remote.dto.IKitapTurApi
import com.mesutemre.kutuphanem.parameter.kitaptur.data.remote.dto.KitapTurDto
import com.mesutemre.kutuphanem.parameter.kitaptur.data.repository.KitapTurRepository
import retrofit2.Response
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 3.07.2022
 */
class KitapTurRepositoryImpl @Inject constructor(
    private val service: IKitapTurApi,
    private val kitapTurDao: IKitapTurDao
) : KitapTurRepository {

    override suspend fun getKitapTurListeByAPI(): Response<List<KitapTurDto>> {
        return service.getKitapTurListe()
    }

    override suspend fun getKitapTurListeByDAO(): List<KitapTurEntity> {
        return kitapTurDao.getKitapTurListe()
    }

    override suspend fun saveKitapTurList(vararg kitapTur: KitapTurEntity) {
        kitapTurDao.kitapTurKaydet(*kitapTur)
    }

    override suspend fun deleteKitapTurList() {
        kitapTurDao.deleteKitapTurList()
    }

    override suspend fun kitapTurKaydet(kitapTurDto: KitapTurDto): Response<ResponseStatusModel> {
        return service.kitapTurKaydet(kitapTurDto)
    }

}