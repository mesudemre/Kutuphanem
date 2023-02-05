package com.mesutemre.kutuphanem.kitap_ekleme.domain.use_case

import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem.kitap_ekleme.data.repository.KitapEklemeRepository
import com.mesutemre.kutuphanem.kitap_ekleme.domain.model.KitapEklemeKitapModel
import com.mesutemre.kutuphanem.kitap_liste.data.remote.dto.KitapDto
import com.mesutemre.kutuphanem.model.ResponseStatusModel
import com.mesutemre.kutuphanem.parameter.kitaptur.data.remote.dto.KitapTurDto
import com.mesutemre.kutuphanem.parameter.yayinevi.data.remote.dto.YayinEviDto
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import com.mesutemre.kutuphanem_base.use_case.IServiceCall
import com.mesutemre.kutuphanem_base.use_case.ServiceCallUseCase
import com.mesutemre.kutuphanem_base.util.convertStringToDate
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class KitapKaydetUseCase @Inject constructor(
    private val kitapEklemeRepository: KitapEklemeRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : IServiceCall by ServiceCallUseCase() {

    operator fun invoke(kitap: KitapEklemeKitapModel): Flow<BaseResourceEvent<ResponseStatusModel?>> {
        val alinmaTar =
            kitap.alinmaTar.split(".")[2] + "-" + kitap.alinmaTar.split(".")[1] + "-" + kitap.alinmaTar.split(
                "."
            )[0]
        return serviceCall {
            kitapEklemeRepository.kitapKaydet(
                KitapDto(
                    kitapAd = kitap.kitapAd,
                    yazarAd = kitap.yazarAd,
                    kitapAciklama = kitap.kitapAciklama,
                    alinmaTar = kitap.alinmaTar.convertStringToDate(),
                    kitapTur = KitapTurDto(
                        id = kitap.kitapTurItem.kitapTurId,
                        aciklama = kitap.kitapTurItem.kitapTurAciklama
                    ),
                    yayinEvi = YayinEviDto(
                        id = kitap.yayinEviItem.yayinEviId,
                        aciklama = kitap.yayinEviItem.yayinEviAciklama
                    )
                )
            )
        }.flowOn(ioDispatcher)
    }
}