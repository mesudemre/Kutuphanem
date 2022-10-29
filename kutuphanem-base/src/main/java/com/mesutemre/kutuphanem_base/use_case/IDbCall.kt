package com.mesutemre.kutuphanem_base.use_case

import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import kotlinx.coroutines.flow.Flow

interface IDbCall {

    fun <T : Any> dbCall(
        call: suspend () -> T
    ): Flow<BaseResourceEvent<T>>
}