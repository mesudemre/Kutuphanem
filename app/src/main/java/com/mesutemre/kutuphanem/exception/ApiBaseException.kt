package com.mesutemre.kutuphanem.exception

data class ApiBaseException(
    val responseMessage:String,
    val responseCode :Int
)
