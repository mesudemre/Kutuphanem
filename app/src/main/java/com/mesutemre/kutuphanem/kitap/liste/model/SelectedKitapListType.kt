package com.mesutemre.kutuphanem.kitap.liste.model

import androidx.annotation.StringDef

@StringDef(API_LISTE, ARSIV, BEGENILENLER)
@Retention(AnnotationRetention.SOURCE)
annotation class SelectedKitapListType

const val API_LISTE:String  = "KitapApiListe";
const val ARSIV:String  = "KitapArsivListe";
const val BEGENILENLER:String    = "KitapBegeniListe";
