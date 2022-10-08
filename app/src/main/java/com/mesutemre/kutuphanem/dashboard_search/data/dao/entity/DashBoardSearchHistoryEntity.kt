package com.mesutemre.kutuphanem.dashboard_search.data.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DashBoardSearchHistoryEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val kitapId: Int?,

    @ColumnInfo(name = "kitapAd")
    val kitapAd: String?,

    @ColumnInfo(name = "yazarAd")
    val yazarAd: String?
)
