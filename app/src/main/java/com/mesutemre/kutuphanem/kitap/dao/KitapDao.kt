package com.mesutemre.kutuphanem.kitap.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mesutemre.kutuphanem.kitap.liste.model.KitapModel

@Dao
interface KitapDao {

    @Query("SELECT * FROM kitapmodel")
    fun getKitapListe():PagingSource<Int, KitapModel>;

    @Insert
    suspend fun kitapKaydet(kitapModel: KitapModel):Unit;

    @Query("DELETE FROM kitapmodel WHERE id=:kitapId")
    suspend fun kitapSil(kitapId:Int):Unit;

    @Query("SELECT * FROM kitapmodel WHERE id=:kitapId")
    suspend fun getKitapById(kitapId: Int): KitapModel;

    @Query("SELECT * FROM kitapmodel")
    suspend fun getArsivKitapListe():MutableList<KitapModel>;

}