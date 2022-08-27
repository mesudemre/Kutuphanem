package com.mesutemre.kutuphanem.exceptions.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mesutemre.kutuphanem.util.DEVICE_NAME
import java.util.*

/**
 * @Author: mesutemre.celenk
 * @Date: 17.01.2022
 */
@Entity
data class KutuphanemGlobalExceptionHandlerModel(

    @PrimaryKey(autoGenerate = true)
    val id:Long,

    @ColumnInfo(name = "detay")
    val detay:String?,

    @ColumnInfo(name = "kayittarihi")
    val kayittarihi: Date = Date(),

    @ColumnInfo(name = "cihazInfo")
    val cihazInfo:String = DEVICE_NAME
){
    constructor(detay: String) : this(0, detay)
}