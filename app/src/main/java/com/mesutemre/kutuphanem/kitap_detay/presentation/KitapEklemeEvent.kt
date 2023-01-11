package com.mesutemre.kutuphanem.kitap_detay.presentation

sealed class KitapEklemeEvent {

    data class KitapAdTextChange(val kitapAd: String) : KitapEklemeEvent()
    data class YazarAdTextChange(val yazarAd: String) : KitapEklemeEvent()
    data class KitapAlinmaTarTextChange(val alinmaTar: String) : KitapEklemeEvent()
    data class KitapAciklamaTextChange(val kitapAciklama: String) : KitapEklemeEvent()
    data class KitapResimEklemeOpenClose(val isOpen: Boolean) : KitapEklemeEvent()
}
