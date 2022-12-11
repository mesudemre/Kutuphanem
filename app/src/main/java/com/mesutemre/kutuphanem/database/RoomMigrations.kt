package com.mesutemre.kutuphanem.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/*
* mesutemre.celenk
* KitapModel classına eklenen begenilmis değişkeni için aşağıdaki migration uygulandı
* 08.09.2021
* */
val MIGRATION_2TO2_1 = object : Migration(2, 2_1) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE kitapmodel ADD COLUMN begenilmis INTEGER NOT NULL DEFAULT 0")
    }
}

/*
* mesutemre.celenk
* KitapEntity classına eklenen kitapPuan,yayinEviId ve kitapTurId kolonları için aşağıdaki migration uygulandı
* 26.11.2022
* */
val MIGRATION_2_3TO2_4 = object : Migration(2_3, 2_4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE KitapEntity ADD COLUMN kitapPuan REAL")
        database.execSQL("ALTER TABLE KitapEntity ADD COLUMN yayinEvi INTEGER")
        database.execSQL("ALTER TABLE KitapEntity ADD COLUMN kitapTur INTEGER ")
        database.execSQL("ALTER TABLE KitapEntity ADD COLUMN alinmaTar TEXT")
    }
}