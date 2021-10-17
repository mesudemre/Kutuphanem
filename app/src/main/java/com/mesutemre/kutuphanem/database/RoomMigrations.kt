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