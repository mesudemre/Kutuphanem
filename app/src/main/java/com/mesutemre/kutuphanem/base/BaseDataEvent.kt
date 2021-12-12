package com.mesutemre.kutuphanem.base

sealed class BaseDataEvent<out T>(){

    data class Success<T>(val data:T?):BaseDataEvent<T>();
    data class Error(val errMessage:String): BaseDataEvent<Nothing>();
}
