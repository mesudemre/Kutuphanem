package com.mesutemre.kutuphanem_base.use_case

import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

/**
 * @Author: mesutemre.celenk
 * @Date: 11.09.2022
 */
class ServiceCallUseCase : IServiceCall {

    override inline fun <T : Any> serviceCall(crossinline call: suspend () -> Response<T>): Flow<BaseResourceEvent<T?>> =
        flow {
            emit(BaseResourceEvent.Loading())
            var response: Response<T>? = null
            try {
                response = call.invoke()
            } catch (t: Throwable) {
                emit(BaseResourceEvent.Error(t.message!!))
            }
            if (response != null) {
                if (!response.isSuccessful) {
                    val errorBody = response.errorBody()
                    emit(BaseResourceEvent.Error(errorBody.toString()))
                } else {
                    emit(BaseResourceEvent.Success(response.body()))
                }
            }
        }
}