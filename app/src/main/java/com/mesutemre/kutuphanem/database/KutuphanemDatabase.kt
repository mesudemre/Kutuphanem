package com.mesutemre.kutuphanem.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mesutemre.kutuphanem.auth.dao.KullaniciDao
import com.mesutemre.kutuphanem.auth.profil.model.Kullanici
import com.mesutemre.kutuphanem.auth.profil.model.KullaniciKitapTurModel
import com.mesutemre.kutuphanem.exceptions.dao.KutuphanemGlobalExceptionHandlerDao
import com.mesutemre.kutuphanem.exceptions.model.KutuphanemGlobalExceptionHandlerModel
import com.mesutemre.kutuphanem.kitap.dao.KitapDao
import com.mesutemre.kutuphanem.kitap.liste.model.KitapModel
import com.mesutemre.kutuphanem.parameter.kitaptur.data.dao.IKitapTurDao
import com.mesutemre.kutuphanem.parameter.kitaptur.data.dao.entity.KitapTurEntity
import com.mesutemre.kutuphanem.parameter.yayinevi.data.dao.entity.IYayinEviDao
import com.mesutemre.kutuphanem.parameter.yayinevi.data.dao.entity.YayinEviEntity
import com.mesutemre.kutuphanem.parametre.dao.ParametreDao
import com.mesutemre.kutuphanem.parametre.kitaptur.model.KitapturModel
import com.mesutemre.kutuphanem.parametre.yayinevi.model.YayineviModel
import com.mesutemre.kutuphanem.util.converters.Converters

@Database(  version = 2_2,
            entities = arrayOf(
                YayineviModel::class,
                KitapturModel::class,
                Kullanici::class,
                KullaniciKitapTurModel::class,
                KitapModel::class,
                KutuphanemGlobalExceptionHandlerModel::class,
                YayinEviEntity::class,
                KitapTurEntity::class
            ),
            exportSchema = false)
@TypeConverters(Converters::class)
abstract class KutuphanemDatabase:RoomDatabase() {

    abstract fun getParametreDao(): ParametreDao
    abstract fun getKullaniciDao(): KullaniciDao
    abstract fun getKitapDao(): KitapDao
    abstract fun getGlobalExceptionDao():KutuphanemGlobalExceptionHandlerDao
    abstract fun getYayinEviDao(): IYayinEviDao
    abstract fun getKitapTurDao(): IKitapTurDao
}