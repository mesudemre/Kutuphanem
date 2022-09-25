package com.mesutemre.kutuphanem.dashboard.data.dao.entity

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface IDashBoardDao {

    @Query("SELECT aciklama,adet FROM KitapTurIstatistikEntity")
    suspend fun getKitapTurIstatistikListe(): List<KitapTurIstatistikEntity>

    @Insert
    suspend fun kitapTurIstatistikKaydet(vararg kitapTurIstatistik: KitapTurIstatistikEntity)

    @Query("DELETE FROM KitapTurIstatistikEntity")
    suspend fun deleteKitapTurIstatistikList()

    @Query("SELECT adet,yil from KitapYilIstatistikEntity")
    suspend fun getKitapYilIstatistikListe(): List<KitapYilIstatistikEntity>

    @Insert
    suspend fun kitapYilIstatistikKaydet(vararg kitapYilIstatistik: KitapYilIstatistikEntity)

    @Query("DELETE FROM KitapYilIstatistikEntity")
    suspend fun deleteKitapYilIstatistikList()
}