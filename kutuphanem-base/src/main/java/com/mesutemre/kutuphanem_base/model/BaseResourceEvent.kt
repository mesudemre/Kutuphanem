package com.mesutemre.kutuphanem_base.model

import androidx.annotation.StringRes

sealed class BaseResourceEvent<T>(
    val data:T? = null,
    val message:String? = null,
    @StringRes val messageId:Int? = null
){
    class Nothing<T> :BaseResourceEvent<T>()
    class Success<T>(data: T) : BaseResourceEvent<T>(data)
    class Error<T>(message: String?, data: T? = null, messageId: Int? = null) : BaseResourceEvent<T>(data, message,messageId)
    class Loading<T> : BaseResourceEvent<T>()
}
