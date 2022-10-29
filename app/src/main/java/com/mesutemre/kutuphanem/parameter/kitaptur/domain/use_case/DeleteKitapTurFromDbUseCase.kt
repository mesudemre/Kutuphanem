package com.mesutemre.kutuphanem.parameter.kitaptur.domain.use_case

import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem.parameter.kitaptur.data.repository.KitapTurRepository
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import com.mesutemre.kutuphanem_base.use_case.DbCallUseCase
import com.mesutemre.kutuphanem_base.use_case.IDbCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 11.09.2022
 */
class DeleteKitapTurFromDbUseCase @Inject constructor(
    private val kitapTurRepository: KitapTurRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): IDbCall by DbCallUseCase() {

    operator fun invoke() : Flow<BaseResourceEvent<Unit>>{
        return dbCall {
            kitapTurRepository.deleteKitapTurList()
        }.flowOn(ioDispatcher)
    }
}