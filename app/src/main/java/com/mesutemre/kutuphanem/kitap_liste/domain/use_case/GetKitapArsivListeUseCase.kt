package com.mesutemre.kutuphanem.kitap_liste.domain.use_case

import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem.kitap_liste.data.repository.KitapListeRepository
import com.mesutemre.kutuphanem.kitap_liste.domain.model.KitapArsivItem
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import com.mesutemre.kutuphanem_base.use_case.DbCallUseCase
import com.mesutemre.kutuphanem_base.use_case.IDbCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetKitapArsivListeUseCase @Inject constructor(
    private val kitapListeRepository: KitapListeRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : IDbCall by DbCallUseCase() {

    operator fun invoke(): Flow<BaseResourceEvent<List<KitapArsivItem>>> {
        return dbCall {
            kitapListeRepository.getKitapArsivListe()
        }
    }
}