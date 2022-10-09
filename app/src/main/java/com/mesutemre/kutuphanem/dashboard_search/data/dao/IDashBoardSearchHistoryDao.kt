package com.mesutemre.kutuphanem.dashboard_search.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mesutemre.kutuphanem.dashboard_search.data.dao.entity.DashBoardSearchHistoryEntity

@Dao
interface IDashBoardSearchHistoryDao {

    @Query("SELECT id,kitapAd,yazarAd FROM DashBoardSearchHistoryEntity ORDER BY id")
    suspend fun getSearchHistoryList(): List<DashBoardSearchHistoryEntity>

    @Insert
    suspend fun searchHistoryKaydet(vararg searchHistory: DashBoardSearchHistoryEntity)

    @Query("DELETE FROM DashBoardSearchHistoryEntity")
    suspend fun deleteSearchHistory()
}