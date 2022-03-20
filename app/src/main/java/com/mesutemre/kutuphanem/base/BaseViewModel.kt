package com.mesutemre.kutuphanem.base

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import retrofit2.Response

/**
 * @Author: mesutemre.celenk
 * @Date: 31.12.2021
 */
abstract class BaseViewModel: ViewModel() {

    inline suspend fun <T:Any> serviceCall(crossinline call: suspend() -> Response<T>,
                                           dispatcher: CoroutineDispatcher
    ):BaseDataEvent<T>{
        return withContext(dispatcher){
            val response: Response<T>
            try {
                response = call.invoke()
            }catch (t:Throwable){
                return@withContext BaseDataEvent.Error(t.message!!)
            }

            return@withContext if (!response.isSuccessful){
                val errorBody = response.errorBody()
                BaseDataEvent.Error(errorBody.toString())
            }else {
                return@withContext if (response.body() == null) {
                    BaseDataEvent.Error("Boş response")
                }else {
                    BaseDataEvent.Success(response.body())
                }
            }
        }

    }

    inline suspend fun <T:Any> dbCall(crossinline call: suspend() -> T,
                                      dispatcher: CoroutineDispatcher
    ):BaseDataEvent<T>{
        return withContext(dispatcher) {
            val response:T
            try {
                response = call.invoke()
            }catch (t:Throwable){
                return@withContext BaseDataEvent.Error(t.message!!)
            }

            return@withContext if (response == null){
                BaseDataEvent.Error("Herhangi bir data bulunamadı!")
            }else {
                BaseDataEvent.Success(response)
            }
        }
    }

    inline fun <T> MutableStateFlow<T>.updateState(function: (T) -> T) {
        while (true) {
            val prevValue = value
            val nextValue = function(prevValue)
            if (compareAndSet(prevValue, nextValue)) {
                return
            }
        }
    }
}