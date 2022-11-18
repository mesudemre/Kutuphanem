package com.mesutemre.kutuphanem.kitap_liste.domain.use_case

import com.mesutemre.kutuphanem.kitap_liste.data.dao.entity.KitapEntity
import com.mesutemre.kutuphanem.model.ResponseStatusModel
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 12.11.2022
 */
class KitapArsivleUseCase @Inject constructor(
    private val kitapResimArsivleUseCase: KitapResimArsivleUseCase,
    private val kitapDbArsivleUseCase: KitapDbArsivleUseCase
) {
    operator fun invoke(
        kitapId: Int,
        kitapAd: String,
        yazarAd: String,
        kitapAciklama: String,
        kitapImageUrl: String
    ) = channelFlow<BaseResourceEvent<ResponseStatusModel?>> {
        send(BaseResourceEvent.Loading())
        kitapResimArsivleUseCase(
            imageUrl = kitapImageUrl,
            kitapId = kitapId,
            kitapAd = kitapAd
        ).collectLatest {
            if (it is BaseResourceEvent.Success) {
                kitapDbArsivleUseCase(
                    kitapEntity = KitapEntity(
                        kitapId = kitapId,
                        kitapAd = kitapAd,
                        yazarAd = yazarAd,
                        kitapAciklama = kitapAciklama,
                        kitapResimPath = it.data?.path
                    )
                ).collectLatest {
                    send(
                        BaseResourceEvent.Success(
                            ResponseStatusModel(
                                "200",
                                "Kitap başarıyla arşivlendi"
                            )
                        )
                    )
                }
            } else if (it is BaseResourceEvent.Error) {
                send(
                    BaseResourceEvent.Success(
                        ResponseStatusModel(
                            "500",
                            "Kitap arşivlenirken hata metdana geldi!"
                        )
                    )
                )
            }
        }
    }
}