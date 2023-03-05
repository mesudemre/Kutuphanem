package com.mesutemre.kutuphanem.profile.data.dao.entity

import androidx.room.Embedded
import androidx.room.Relation

data class KullaniciBilgiCinsiyetEntity(
    @Embedded
    val kullaniciBilgi: KullaniciBilgiEntity,

    @Relation(
        parentColumn = "kullaniciad",
        entityColumn = "kullaniciad"
    )
    val cinsiyet: KullaniciCinsiyetEntity
)
