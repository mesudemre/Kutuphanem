package com.mesutemre.kutuphanem.kitap_liste.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mesutemre.kutuphanem.kitap_liste.data.dao.entity.KitapEntity

@Dao
interface IKitapDao {

    @Insert
    suspend fun saveKitap(kitapEntity: KitapEntity)

    @Query("SELECT * FROM KitapEntity")
    suspend fun getKitapArsivListe(): List<KitapEntity>

    @Query("DELETE FROM KitapEntity WHERE id=:kitapId")
    suspend fun kitapSil(kitapId: Int)
}