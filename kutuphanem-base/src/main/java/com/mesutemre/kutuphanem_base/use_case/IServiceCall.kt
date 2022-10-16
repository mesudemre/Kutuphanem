package com.mesutemre.kutuphanem_base.use_case

import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface IServiceCall {

    fun <T : Any> serviceCall(
        call: suspend () -> Response<T>
    ): Flow<BaseResourceEvent<T?>>
}