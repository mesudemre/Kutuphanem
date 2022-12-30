package com.mesutemre.kutuphanem.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mesutemre.kutuphanem.auth.dao.KullaniciDao
import com.mesutemre.kutuphanem.auth.profil.model.Kullanici
import com.mesutemre.kutuphanem.auth.profil.model.KullaniciKitapTurModel
import com.mesutemre.kutuphanem.dashboard.data.dao.entity.IDashBoardDao
import com.mesutemre.kutuphanem.dashboard.data.dao.entity.KitapYilIstatistikEntity
import com.mesutemre.kutuphanem.dashboard_search.data.dao.IDashBoardSearchHistoryDao
import com.mesutemre.kutuphanem.dashboard_search.data.dao.entity.DashBoardSearchHistoryEntity
import com.mesutemre.kutuphanem.exceptions.dao.KutuphanemGlobalExceptionHandlerDao
import com.mesutemre.kutuphanem.exceptions.model.KutuphanemGlobalExceptionHandlerModel
import com.mesutemre.kutuphanem.kitap.dao.KitapDao
import com.mesutemre.kutuphanem.kitap.liste.model.KitapModel
import com.mesutemre.kutuphanem.kitap_detay.data.dao.IKitapDetayDao
import com.mesutemre.kutuphanem.kitap_liste.data.dao.IKitapDao
import com.mesutemre.kutuphanem.kitap_liste.data.dao.entity.KitapEntity
import com.mesutemre.kutuphanem.parameter.kitaptur.data.dao.IKitapTurDao
import com.mesutemre.kutuphanem.parameter.kitaptur.data.dao.entity.KitapTurEntity
import com.mesutemre.kutuphanem.parameter.yayinevi.data.dao.entity.IYayinEviDao
import com.mesutemre.kutuphanem.parameter.yayinevi.data.dao.entity.YayinEviEntity
import com.mesutemre.kutuphanem.util.converters.Converters

@Database(
    version = 2_4,
    entities = arrayOf(
        Kullanici::class,
        KullaniciKitapTurModel::class,
        KitapModel::class,
        KutuphanemGlobalExceptionHandlerModel::class,
        YayinEviEntity::class,
        KitapTurEntity::class,
        KitapYilIstatistikEntity::class,
        DashBoardSearchHistoryEntity::class,
        KitapEntity::class
    ),
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class KutuphanemDatabase : RoomDatabase() {

    abstract fun getKullaniciDao(): KullaniciDao
    abstract fun getKitapDao(): KitapDao
    abstract fun getGlobalExceptionDao(): KutuphanemGlobalExceptionHandlerDao
    abstract fun getYayinEviDao(): IYayinEviDao
    abstract fun getKitapTurDao(): IKitapTurDao
    abstract fun getDashBoardDao(): IDashBoardDao
    abstract fun getDashBoardSearchHistoryDao(): IDashBoardSearchHistoryDao
    abstract fun getKitapListeDao(): IKitapDao
    abstract fun getKitapDetayDao(): IKitapDetayDao
}