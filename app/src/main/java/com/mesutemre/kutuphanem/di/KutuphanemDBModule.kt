package com.mesutemre.kutuphanem.di

import android.content.Context
import androidx.room.Room
import com.mesutemre.kutuphanem.database.KutuphanemDatabase
import com.mesutemre.kutuphanem.database.MIGRATION_2TO2_1
import com.mesutemre.kutuphanem.util.KUTUPHANEM_DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @Author: mesutemre.celenk
 * @Date: 12.09.2021
 */
@Module
@InstallIn(SingletonComponent::class)
class KutuphanemDBModule {

    @Singleton
    @Provides
    fun provideKutuphanemDatabase
                (@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        KutuphanemDatabase::class.java,
        KUTUPHANEM_DB_NAME
    )
        .addMigrations(MIGRATION_2TO2_1)
        .build();

    @Singleton
    @Provides
    fun provideParametreDao(database: KutuphanemDatabase) = database.getParametreDao();

    @Singleton
    @Provides
    fun provideKullaniciDao(database: KutuphanemDatabase) = database.getKullaniciDao();

    @Singleton
    @Provides
    fun provideKitapDao(database: KutuphanemDatabase) = database.getKitapDao();
}