package com.mesutemre.kutuphanem.parameter.yayinevi.data.dao.entity

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface IYayinEviDao {

    @Query("SELECT id,aciklama FROM YayinEviEntity ORDER BY aciklama")
    suspend fun getYayinEviListe(): List<YayinEviEntity>

    @Insert
    suspend fun yayinEviKaydet(vararg yayinEvi: YayinEviEntity)

    @Query("DELETE FROM YayinEviEntity")
    suspend fun deleteYayinEviList()
}