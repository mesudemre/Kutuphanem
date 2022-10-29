package com.mesutemre.kutuphanem.parameter.yayinevi.domain.use_case

import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem.parameter.yayinevi.domain.repository.YayinEviRepository
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
class DeleteYayinEviFromDbUseCase @Inject constructor(
    private val yayinEviRepository: YayinEviRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): IDbCall by DbCallUseCase() {

    operator fun invoke() : Flow<BaseResourceEvent<Unit>> {
        return dbCall {
            yayinEviRepository.deleteYayinEviList()
        }.flowOn(ioDispatcher)
    }
}