package com.mesutemre.kutuphanem.kitap_detay.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.mesutemre.kutuphanem.kitap_detay.data.dao.entity.KitapEntityWithYayinEviKitapTur

@Dao
interface IKitapDetayDao {

    @Query(
        "SELECT *" +
                " FROM KitapEntity k " +
                " LEFT JOIN KitapTurEntity kt ON k.kitapTur=kt.id " +
                " LEFT JOIN YayinEviEntity ye ON k.yayinEvi=ye.id " +
                " WHERE k.id=:kitapId "
    )
    suspend fun getKitapById(kitapId: Int): KitapEntityWithYayinEviKitapTur

}