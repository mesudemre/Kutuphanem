package com.mesutemre.kutuphanem.base

import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface IServiceCall {

    fun <T : Any> serviceCall(
        call: suspend () -> Response<T>
    ): Flow<BaseResourceEvent<T?>>
}