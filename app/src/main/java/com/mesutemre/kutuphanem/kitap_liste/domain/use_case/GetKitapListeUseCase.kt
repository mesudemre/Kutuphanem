package com.mesutemre.kutuphanem.kitap_liste.domain.use_case

import com.mesutemre.kutuphanem.kitap_liste.data.repository.KitapListeRepository
import com.mesutemre.kutuphanem_base.use_case.IServiceCall
import com.mesutemre.kutuphanem_base.use_case.ServiceCallUseCase
import javax.inject.Inject

class GetKitapListeUseCase @Inject constructor(
    private val kitapListeRepository: KitapListeRepository
): IServiceCall by ServiceCallUseCase() {

    operator suspend fun invoke(
        start: Int,
        end:Int
    ) {
        
    }
}