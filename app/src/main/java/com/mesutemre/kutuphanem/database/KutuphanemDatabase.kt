package com.mesutemre.kutuphanem.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mesutemre.kutuphanem.auth.profil.model.Kullanici
import com.mesutemre.kutuphanem.util.converters.Converters
import com.mesutemre.kutuphanem.kitap.dao.KitapDao
import com.mesutemre.kutuphanem.auth.dao.KullaniciDao
import com.mesutemre.kutuphanem.auth.profil.model.KullaniciKitapTurModel
import com.mesutemre.kutuphanem.kitap.liste.model.KitapModel
import com.mesutemre.kutuphanem.parametre.dao.ParametreDao
import com.mesutemre.kutuphanem.parametre.kitaptur.model.KitapturModel
import com.mesutemre.kutuphanem.parametre.yayinevi.model.YayineviModel

@Database(  version = 2_1,
            entities = arrayOf(
                YayineviModel::class,
                KitapturModel::class,
                Kullanici::class,
                KullaniciKitapTurModel::class,
                KitapModel::class
            ),
            exportSchema = false)
@TypeConverters(Converters::class)
abstract class KutuphanemDatabase:RoomDatabase() {

    abstract fun getParametreDao(): ParametreDao;
    abstract fun getKullaniciDao(): KullaniciDao;
    abstract fun getKitapDao(): KitapDao;

}