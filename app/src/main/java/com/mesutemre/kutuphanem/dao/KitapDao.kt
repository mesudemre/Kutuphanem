package com.mesutemre.kutuphanem.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mesutemre.kutuphanem.model.KitapModel

@Dao
interface KitapDao {

    @Query("SELECT * FROM kitapmodel")
    suspend fun getKitapListe():List<KitapModel>;

    @Insert
    suspend fun kitapKaydet(kitapModel: KitapModel):Unit;

    @Query("DELETE FROM kitapmodel WHERE id=:kitapId")
    suspend fun kitapSil(kitapId:Int):Unit;

    @Query("SELECT * FROM kitapmodel WHERE id=:kitapId")
    suspend fun getKitapById(kitapId: Int):KitapModel;
}