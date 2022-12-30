package com.mesutemre.kutuphanem.kitap_detay.presentation

import com.mesutemre.kutuphanem.dashboard.domain.model.DashboardKullaniciBilgiData
import com.mesutemre.kutuphanem.kitap_detay.domain.model.KitapDetayBottomSheetState
import com.mesutemre.kutuphanem.kitap_detay.domain.model.KitapDetayBottomSheetYorumModel
import com.mesutemre.kutuphanem.kitap_detay.domain.model.KitapDetayBottomsheetYorumListModel
import com.mesutemre.kutuphanem.kitap_detay.domain.model.KitapDetayItem
import com.mesutemre.kutuphanem.model.ResponseStatusModel
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent

data class KitapDetayScreenState(
    val kitapId: Int = 0,
    val isFromArsiv: Boolean = false,
    val kitapDetayItemResource: BaseResourceEvent<KitapDetayItem> = BaseResourceEvent.Loading(),
    val kitapDetayBottomSheetState: KitapDetayBottomSheetState? = null,
    val kitapDetayAciklama: String? = null,
    val kitapYorumListeResouce: BaseResourceEvent<List<KitapDetayBottomSheetYorumModel>> = BaseResourceEvent.Loading(),
    val yorumListeModel: List<KitapDetayBottomsheetYorumListModel>? = null,
    val yorumText: String = "",
    val userInfo: DashboardKullaniciBilgiData? = null,
    val kitapYorumKaydetResource: BaseResourceEvent<ResponseStatusModel?> = BaseResourceEvent.Nothing()
)
