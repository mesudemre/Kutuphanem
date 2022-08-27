package com.mesutemre.kutuphanem.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.Response

/**
 * @Author: mesutemre.celenk
 * @Date: 27.02.2022
 */
open class BaseUseCase{

    inline fun <T : Any> serviceCall(
        crossinline call: suspend () -> Response<T>
    ): Flow<BaseResourceEvent<T?>> = flow {
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

    inline fun <T : Any> dbCall(
        crossinline call: suspend () -> T
    ): Flow<BaseResourceEvent<out T?>> = flow {
        emit(BaseResourceEvent.Loading())
        var response: T? = null
        try {
            response = call.invoke()
        } catch (t: Throwable) {
            emit(BaseResourceEvent.Error(t.message!!))
        }
        if (response == null) {
            emit(BaseResourceEvent.Error("Herhangi bir data bulunamadı!"))
        }else {
            emit(BaseResourceEvent.Success(response))
        }
    }

    suspend inline fun <T : Any> nonFlowServiceCall(
        dispatcher: CoroutineDispatcher,
        crossinline call: suspend () -> Response<T>
    ): BaseResourceEvent<T> {
        return withContext(dispatcher) {
            val response: Response<T>
            try {
                response = call.invoke()
            } catch (t: Throwable) {
                return@withContext BaseResourceEvent.Error(t.message!!)
            }

            return@withContext if (!response.isSuccessful){
                val errorBody = response.errorBody()
                BaseResourceEvent.Error(errorBody.toString())
            }else {
                return@withContext if (response.body() == null) {
                    BaseResourceEvent.Error("Boş response")
                }else {
                    BaseResourceEvent.Success(response.body()!!)
                }
            }
        }
    }

    inline suspend  fun <T : Any> nonFlowDbCall(
        dispatcher: CoroutineDispatcher,
        crossinline call: suspend () -> T
    ): BaseResourceEvent<T>{
        return withContext(dispatcher) {
            var response: T
            try {
                response = call.invoke()
            } catch (t: Throwable) {
                return@withContext BaseResourceEvent.Error(t.message!!)
            }
            if (response == null) {
                BaseResourceEvent.Error("Herhangi bir data bulunamadı!")
            }else {
                BaseResourceEvent.Success(response)
            }
        }
    }
}