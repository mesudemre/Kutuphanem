package com.mesutemre.kutuphanem.parameter.yayinevi.data.repository

import com.mesutemre.kutuphanem.model.ResponseStatusModel
import com.mesutemre.kutuphanem.parameter.yayinevi.data.dao.entity.IYayinEviDao
import com.mesutemre.kutuphanem.parameter.yayinevi.data.dao.entity.YayinEviEntity
import com.mesutemre.kutuphanem.parameter.yayinevi.data.remote.dto.IYayinEviApi
import com.mesutemre.kutuphanem.parameter.yayinevi.data.remote.dto.YayinEviDto
import com.mesutemre.kutuphanem.parameter.yayinevi.domain.repository.YayinEviRepository
import retrofit2.Response
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 15.05.2022
 */
class YayinEviRepositoryImpl @Inject constructor(
    private val service: IYayinEviApi,
    private val dao: IYayinEviDao
) : YayinEviRepository {

    override suspend fun getYayinEviLisetByAPI(): Response<List<YayinEviDto>> {
        return service.getYayinEviListe()
    }

    override suspend fun getYayinEviListeByDAO(): List<YayinEviEntity> {
        return dao.getYayinEviListe()
    }

    override suspend fun saveYayinEviList(vararg yayinEvi: YayinEviEntity) {
        dao.yayinEviKaydet(*yayinEvi)
    }

    override suspend fun deleteYayinEviList() {
        dao.deleteYayinEviList()
    }

    override suspend fun yayinEviKaydet(yayinEviDto: YayinEviDto): Response<ResponseStatusModel> {
        return service.yayinEviKaydet(yayinEviDto)
    }
}