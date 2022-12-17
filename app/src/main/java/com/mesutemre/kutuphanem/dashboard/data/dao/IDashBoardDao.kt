package com.mesutemre.kutuphanem.dashboard.data.dao.entity

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface IDashBoardDao {

    @Query("SELECT adet,yil from KitapYilIstatistikEntity")
    suspend fun getKitapYilIstatistikListe(): List<KitapYilIstatistikEntity>

    @Insert
    suspend fun kitapYilIstatistikKaydet(vararg kitapYilIstatistik: KitapYilIstatistikEntity)

    @Query("DELETE FROM KitapYilIstatistikEntity")
    suspend fun deleteKitapYilIstatistikList()
}