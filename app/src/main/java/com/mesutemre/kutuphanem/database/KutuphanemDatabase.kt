package com.mesutemre.kutuphanem.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mesutemre.kutuphanem.converters.Converters
import com.mesutemre.kutuphanem.dao.KullaniciDao
import com.mesutemre.kutuphanem.dao.ParametreDao
import com.mesutemre.kutuphanem.model.KitapturModel
import com.mesutemre.kutuphanem.model.Kullanici
import com.mesutemre.kutuphanem.model.YayineviModel
import com.mesutemre.kutuphanem.model.KullaniciKitapTurModel

@Database(  version = 1,
            entities = arrayOf(YayineviModel::class,KitapturModel::class,Kullanici::class,KullaniciKitapTurModel::class),
            exportSchema = false)
@TypeConverters(Converters::class)
abstract class KutuphanemDatabase:RoomDatabase() {

    abstract fun getParametreDao():ParametreDao;
    abstract fun getKullaniciDao():KullaniciDao;

}