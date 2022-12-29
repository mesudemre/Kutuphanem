package com.mesutemre.kutuphanem.kitap_detay.domain.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.google.gson.Gson
import com.mesutemre.kutuphanem.dashboard.domain.model.DashboardKullaniciBilgiData
import com.mesutemre.kutuphanem.kitap_detay.data.dao.IKitapDetayDao
import com.mesutemre.kutuphanem.kitap_detay.data.dao.entity.KitapEntityWithYayinEviKitapTur
import com.mesutemre.kutuphanem.kitap_detay.data.remote.IKitapYorumApi
import com.mesutemre.kutuphanem.kitap_detay.data.remote.dto.YorumListeDto
import com.mesutemre.kutuphanem.kitap_detay.data.repository.KitapDetayRepository
import com.mesutemre.kutuphanem.kitap_liste.data.remote.IKitapApi
import com.mesutemre.kutuphanem.kitap_liste.data.remote.dto.KitapDto
import com.mesutemre.kutuphanem.util.readString
import kotlinx.coroutines.flow.first
import retrofit2.Response
import javax.inject.Inject

class KitapDetayRepositoryImpl @Inject constructor(
    private val api: IKitapApi,
    private val dao: IKitapDetayDao,
    private val yorumApi: IKitapYorumApi,
    private val dataStore: DataStore<Preferences>
) : KitapDetayRepository {

    override suspend fun getKitapFromAPIById(kitapId: Int): Response<List<KitapDto>> {
        return api.getKitapListe(
            KitapDto(
                id = kitapId
            )
        )
    }

    override suspend fun getKitapFromDbById(kitapId: Int): KitapEntityWithYayinEviKitapTur {
        return dao.getKitapById(kitapId)
    }

    override suspend fun getKitapYorumListeByKitapId(kitapId: Int): Response<YorumListeDto> {
        return yorumApi.getKitapYorumListe(kitapId)
    }

    override suspend fun getDashboardUserInfo(): DashboardKullaniciBilgiData {
        val data = dataStore.readString("TEMP_USER_INFO", "")
        return Gson().fromJson(data.first(), DashboardKullaniciBilgiData::class.java)
    }
}