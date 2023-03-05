package com.mesutemre.kutuphanem.profile.presentation

import com.mesutemre.kutuphanem.profile.domain.model.KullaniciBilgiModel
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent

data class ProfileScreenState(
    val kullaniciBilgiResourceEvent: BaseResourceEvent<KullaniciBilgiModel> = BaseResourceEvent.Nothing()
)
