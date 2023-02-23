package com.mesutemre.kutuphanem.kitap_ekleme.domain.use_case

import com.mesutemre.kutuphanem.kitap_detay.presentation.KitapEklemeEvent
import com.mesutemre.kutuphanem.model.ValidationResult
import javax.inject.Inject

class KitapEklemeFormValidationUseCase @Inject constructor(
    private val kitapAdValidationUseCase: KitapAdValidationUseCase,
    private val yazarAdValidationUseCase: YazarAdValidationUseCase,
    private val alinmaTarValidationUseCase: AlinmaTarValidationUseCase,
    private val kitapAciklamaValidationUseCase: KitapAciklamaValidationUseCase,
    private val kitapTurSelectionValidationUseCase: KitapTurSelectionValidationUseCase,
    private val yayinEviSelectionValidationUseCase: YayinEviSelectionValidationUseCase,
    private val kitapResimValidationUseCase: KitapResimValidationUseCase
) {

    operator fun invoke(event: KitapEklemeEvent): ValidationResult {
        return when (event) {
            is KitapEklemeEvent.KitapAdTextChange -> {
                kitapAdValidationUseCase(event.kitapAd)
            }
            is KitapEklemeEvent.YazarAdTextChange -> {
                yazarAdValidationUseCase(event.yazarAd)
            }
            is KitapEklemeEvent.KitapAlinmaTarTextChange -> {
                alinmaTarValidationUseCase(event.alinmaTar)
            }
            is KitapEklemeEvent.KitapAciklamaTextChange -> {
                kitapAciklamaValidationUseCase(event.kitapAciklama)
            }
            is KitapEklemeEvent.OnSelectKitapTur -> {
                kitapTurSelectionValidationUseCase(event.kitapEklemeKitapTurItem)
            }
            is KitapEklemeEvent.OnSelectYayinEvi -> {
                yayinEviSelectionValidationUseCase(event.kitapEklemeYayinEviItem)
            }
            is KitapEklemeEvent.OnKitapResimCropped -> {
                kitapResimValidationUseCase(event.croppedImageFile)
            }
            else -> {
                ValidationResult(
                    successfullValidate = true
                )
            }
        }
    }
}