package com.mesutemre.kutuphanem.parameter.kitaptur.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mesutemre.kutuphanem.parameter.kitaptur.data.dao.entity.KitapTurEntity

@Dao
interface IKitapTurDao {

    @Query("SELECT id,aciklama FROM KitapTurEntity ORDER BY aciklama")
    suspend fun getKitapTurListe(): List<KitapTurEntity>

    @Insert
    suspend fun kitapTurKaydet(vararg yayinEvi: KitapTurEntity)

    @Query("DELETE FROM KitapTurEntity")
    suspend fun deleteKitapTurList()
}