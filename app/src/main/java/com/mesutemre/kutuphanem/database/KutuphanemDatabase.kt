package com.mesutemre.kutuphanem.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mesutemre.kutuphanem.converters.Converters
import com.mesutemre.kutuphanem.dao.KitapDao
import com.mesutemre.kutuphanem.dao.KullaniciDao
import com.mesutemre.kutuphanem.dao.ParametreDao
import com.mesutemre.kutuphanem.model.*

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

    abstract fun getParametreDao():ParametreDao;
    abstract fun getKullaniciDao():KullaniciDao;
    abstract fun getKitapDao():KitapDao;

}