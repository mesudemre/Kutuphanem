package com.mesutemre.kutuphanem.exceptions.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mesutemre.kutuphanem.exceptions.model.KutuphanemGlobalExceptionHandlerModel

@Dao
interface KutuphanemGlobalExceptionHandlerDao {

    @Query("SELECT * FROM KutuphanemGlobalExceptionHandlerModel")
    suspend fun getExceptionList():List<KutuphanemGlobalExceptionHandlerModel>

    @Insert
    fun exceptionKaydet(model:KutuphanemGlobalExceptionHandlerModel)

    @Query("DELETE FROM KutuphanemGlobalExceptionHandlerModel")
    suspend fun deleteExceptionList()
}