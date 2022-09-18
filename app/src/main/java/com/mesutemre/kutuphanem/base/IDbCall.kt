package com.mesutemre.kutuphanem.base

import kotlinx.coroutines.flow.Flow

interface IDbCall {

    fun <T : Any> dbCall(
        call: suspend () -> T
    ): Flow<BaseResourceEvent<T>>
}