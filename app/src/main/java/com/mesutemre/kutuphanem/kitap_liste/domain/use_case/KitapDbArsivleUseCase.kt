package com.mesutemre.kutuphanem.kitap_liste.domain.use_case

import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem.kitap_liste.data.dao.entity.KitapEntity
import com.mesutemre.kutuphanem.kitap_liste.data.repository.KitapListeRepository
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import com.mesutemre.kutuphanem_base.use_case.DbCallUseCase
import com.mesutemre.kutuphanem_base.use_case.IDbCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 12.11.2022
 */
class KitapDbArsivleUseCase @Inject constructor(
    private val kitapListeRepository: KitapListeRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : IDbCall by DbCallUseCase() {

    operator fun invoke(kitapEntity: KitapEntity): Flow<BaseResourceEvent<Unit>> {
        return dbCall {
            kitapListeRepository.kitapArsivKaydet(kitapEntity)
        }.flowOn(ioDispatcher)
    }
}