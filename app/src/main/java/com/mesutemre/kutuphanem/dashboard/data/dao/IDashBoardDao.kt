package com.mesutemre.kutuphanem.dashboard.data.dao.entity

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface IDashBoardDao {

    @Query("SELECT aciklama,adet FROM KitapTurIstatistikEntity")
    suspend fun getKitapTurIstatistikListe(): List<KitapTurIstatistikEntity>

    @Insert
    suspend fun kitapTurIstatistikKaydet(vararg yayinEvi: KitapTurIstatistikEntity)

    @Query("DELETE FROM KitapTurIstatistikEntity")
    suspend fun deleteKitapTurIstatistikList()
}