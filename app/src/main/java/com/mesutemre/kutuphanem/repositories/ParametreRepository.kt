package com.mesutemre.kutuphanem.repositories

import com.mesutemre.kutuphanem.dao.ParametreDao
import com.mesutemre.kutuphanem.model.KitapturModel
import com.mesutemre.kutuphanem.model.YayineviModel
import javax.inject.Inject

class ParametreRepository @Inject constructor(
    private val parametreDao:ParametreDao
) {
    suspend fun yayinEviParametreKaydet(vararg yayinEvi: YayineviModel) = parametreDao.yayinEviParametreKaydet(*yayinEvi);
    suspend fun getYayinEviListe() = parametreDao.getYayinEviListe();
    suspend fun deleteYayinEviListe() = parametreDao.deleteYayinEviListe();
    suspend fun kitapTurParametreKaydet(vararg kitapTur:KitapturModel) = parametreDao.kitapTurParametreKaydet(*kitapTur);
    suspend fun getKitapTurListe() = parametreDao.getKitapTurListe();
    suspend fun deleteKitapTurListe() = parametreDao.deleteKitapTurListe();
}