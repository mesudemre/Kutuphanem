package com.mesutemre.kutuphanem.base

sealed class BaseResourceEvent<T>(
    val data:T? = null,
    val message:String? = null
){
    class Nothing<T> :BaseResourceEvent<T>()
    class Success<T>(data: T) : BaseResourceEvent<T>(data)
    class Error<T>(message: String?, data: T? = null) : BaseResourceEvent<T>(data, message)
    class Loading<T> : BaseResourceEvent<T>()
}
