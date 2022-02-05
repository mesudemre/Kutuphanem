package com.mesutemre.kutuphanem.model

import androidx.annotation.StringDef

@StringDef(KITAP_ARSIV,
    KITAP_DOWNLOADS,
    KITAP_EKLEME_PHOTO,
    PROFIL_RESIM_EKLEME_PHOTO)
@Retention(AnnotationRetention.SOURCE)
annotation class FileFolders

const val KITAP_ARSIV = "arsiv"
const val KITAP_DOWNLOADS = "kitapdownloads"
const val KITAP_EKLEME_PHOTO = "photoKitapEkleme"
const val PROFIL_RESIM_EKLEME_PHOTO = "photoProfilEkleme"
