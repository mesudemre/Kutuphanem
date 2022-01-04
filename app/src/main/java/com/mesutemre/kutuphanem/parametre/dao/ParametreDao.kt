package com.mesutemre.kutuphanem.parametre.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mesutemre.kutuphanem.parametre.kitaptur.model.KitapturModel
import com.mesutemre.kutuphanem.parametre.yayinevi.model.YayineviModel

@Dao
interface ParametreDao {

    @Insert
    suspend fun yayinEviParametreKaydet(vararg yayinEvi: YayineviModel):Unit;

    @Query("SELECT id,aciklama FROM YayineviModel")
    suspend fun getYayinEviListe():List<YayineviModel>;

    @Query("DELETE FROM YayineviModel")
    suspend fun deleteYayinEviListe():Unit;

    @Insert
    suspend fun kitapTurParametreKaydet(vararg kitapturModel: KitapturModel):Unit;

    @Query("SELECT id,aciklama FROM KitapturModel")
    suspend fun getKitapTurListe():List<KitapturModel>;

    @Query("DELETE FROM KitapturModel")
    suspend fun deleteKitapTurListe():Unit;
}