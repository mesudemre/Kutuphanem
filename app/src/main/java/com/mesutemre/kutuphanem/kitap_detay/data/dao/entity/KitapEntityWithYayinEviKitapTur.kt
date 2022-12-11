package com.mesutemre.kutuphanem.kitap_detay.data.dao.entity

import androidx.room.Embedded
import com.mesutemre.kutuphanem.kitap_detay.domain.model.KitapDetayItem
import com.mesutemre.kutuphanem.kitap_liste.data.dao.entity.KitapEntity
import com.mesutemre.kutuphanem.parameter.kitaptur.data.dao.entity.KitapTurEntity
import com.mesutemre.kutuphanem.parameter.yayinevi.data.dao.entity.YayinEviEntity

data class KitapEntityWithYayinEviKitapTur(
    @Embedded val kitap: KitapEntity,
    @Embedded(prefix = "kitap_kitapTur") val kitapTur: KitapTurEntity,
    @Embedded(prefix = "kitap_yayinEvi") val yayinEvi: YayinEviEntity
)

fun KitapEntityWithYayinEviKitapTur.convertKitapWithYayinEviKitapTurToKitapDetayItem(): KitapDetayItem {
    return KitapDetayItem(
        kitapId = this.kitap.kitapId ?: 0,
        kitapAd = this.kitap.kitapAd ?: "",
        yazarAd = this.kitap.yazarAd ?: "",
        kitapResim = this.kitap.kitapResimPath ?: "",
        kitapAciklama = this.kitap.kitapAciklama ?: "",
        kitapPuan = this.kitap.kitapPuan,
        kitapTurAd = this.kitapTur.aciklama,
        yayinEviAd = this.yayinEvi.aciklama,
        alinmaTar = this.kitap.alinmaTar
    )
}
