package com.mesutemre.kutuphanem.kitap_liste.domain.repository

import com.mesutemre.kutuphanem.kitap_liste.data.dao.IKitapDao
import com.mesutemre.kutuphanem.kitap_liste.data.dao.entity.KitapEntity
import com.mesutemre.kutuphanem.kitap_liste.data.dao.entity.convertKitapEntityItemToKitapArsivItem
import com.mesutemre.kutuphanem.kitap_liste.data.remote.IKitapApi
import com.mesutemre.kutuphanem.kitap_liste.data.remote.dto.KitapDto
import com.mesutemre.kutuphanem.kitap_liste.data.repository.KitapListeRepository
import com.mesutemre.kutuphanem.kitap_liste.domain.model.KitapArsivItem
import com.mesutemre.kutuphanem.model.ResponseStatusModel
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 26.11.2022
 */
class KitapListeRepositoryImpl @Inject constructor(
    private val api: IKitapApi,
    private val dao: IKitapDao
) : KitapListeRepository {

    override suspend fun getKitapListe(kitapDto: KitapDto): Response<List<KitapDto>> {
        return api.getKitapListe(kitapDto)
    }

    override suspend fun kitapArsivKaydet(kitapEntity: KitapEntity) {
        dao.saveKitap(kitapEntity)
    }

    override suspend fun getKitapArsivListe(): List<KitapArsivItem> {
        val list = dao.getKitapArsivListe()
        return list.map {
            it.convertKitapEntityItemToKitapArsivItem()
        }
    }

    override suspend fun deleteKitapById(kitapId: Int) {
        dao.kitapSil(kitapId)
    }

    override suspend fun downloadKitapResim(imageUrl: String): Response<ResponseBody> {
        return api.downloadKitapResim(imageUrl)
    }

    override suspend fun kitapBegen(kitapDto: KitapDto): Response<ResponseStatusModel> {
        return api.kitapBegen(kitapDto)
    }
}