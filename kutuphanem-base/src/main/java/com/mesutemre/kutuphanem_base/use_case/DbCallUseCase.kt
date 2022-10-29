package com.mesutemre.kutuphanem_base.use_case

import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * @Author: mesutemre.celenk
 * @Date: 11.09.2022
 */
class DbCallUseCase : IDbCall {

    override fun <T : Any> dbCall(call: suspend () -> T): Flow<BaseResourceEvent<T>> = flow {
        emit(BaseResourceEvent.Loading())
        var response: T? = null
        try {
            response = call.invoke()
        } catch (t: Throwable) {
            emit(BaseResourceEvent.Error(t.message!!))
        }
        if (response == null) {
            emit(BaseResourceEvent.Error("Herhangi bir data bulunamadı!"))
        } else {
            emit(BaseResourceEvent.Success(response))
        }
    }

}