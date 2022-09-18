package com.mesutemre.kutuphanem.parameter.kitaptur.domain.use_case

import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.base.DbCallUseCase
import com.mesutemre.kutuphanem.base.IDbCall
import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem.parameter.kitaptur.data.dao.entity.KitapTurEntity
import com.mesutemre.kutuphanem.parameter.kitaptur.data.repository.KitapTurRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 11.09.2022
 */
class SaveKitapTurIntoDbUseCase @Inject constructor(
    private val kitapTurRepository: KitapTurRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): IDbCall by DbCallUseCase(){

    operator fun invoke(kitapTurEntityList: List<KitapTurEntity>): Flow<BaseResourceEvent<Unit>> {
        return dbCall {
            kitapTurRepository.saveKitapTurList(*kitapTurEntityList.toTypedArray())
        }.flowOn(ioDispatcher)
    }
}