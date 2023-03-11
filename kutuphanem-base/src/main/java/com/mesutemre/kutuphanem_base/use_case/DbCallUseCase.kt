package com.mesutemre.kutuphanem_base.use_case

import android.util.Log
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * @Author: mesutemre.celenk
 * @Date: 11.09.2022
 */
class DbCallUseCase : IDbCall {

    override inline fun <T : Any> dbCall(crossinline call: suspend () -> T): Flow<BaseResourceEvent<T>> =
        flow {
            emit(BaseResourceEvent.Loading())
            var response: T? = null
            try {
                response = call.invoke()
            } catch (t: Throwable) {
                Log.e("DB_ERROR", t.message ?: "DB komutu çalışmadı....")
                emit(BaseResourceEvent.Error(t.message!!))
            }
            if (response == null) {
                Log.e("DB_ERROR", "Herhangi bir data bulunamadı")
                emit(BaseResourceEvent.Error("Herhangi bir data bulunamadı!"))
            } else {
                emit(BaseResourceEvent.Success(response))
            }
        }

}