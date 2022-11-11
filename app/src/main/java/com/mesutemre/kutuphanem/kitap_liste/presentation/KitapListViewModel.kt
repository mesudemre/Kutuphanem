package com.mesutemre.kutuphanem.kitap_liste.presentation

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.mesutemre.kutuphanem.kitap_liste.domain.model.KitapArsivItem
import com.mesutemre.kutuphanem.kitap_liste.domain.model.SelectedListType
import com.mesutemre.kutuphanem.kitap_liste.domain.use_case.GetKitapArsivListeUseCase
import com.mesutemre.kutuphanem.kitap_liste.domain.use_case.GetKitapListeUseCase
import com.mesutemre.kutuphanem.kitap_liste.domain.use_case.KitapArsivleUseCase
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import com.mesutemre.kutuphanem_base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KitapListViewModel @Inject constructor(
    private val getKitapListeUseCase: GetKitapListeUseCase,
    private val getKitapArsivListeUseCase: GetKitapArsivListeUseCase,
    private val kitapArsivleUseCase: KitapArsivleUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(KitapListState())
    val state: StateFlow<KitapListState> = _state

    init {
        _state.update {
            it.copy(
                kitapListItemPageData = getKitapListeUseCase().cachedIn(viewModelScope)
            )
        }
    }

    fun setSelectedListType(listType: SelectedListType) {
        _state.update {
            it.copy(
                selectedListType = listType
            )
        }
        when (listType.ordinal) {
            SelectedListType.ARSIV.ordinal -> {
                loadKitapArsivListe()
            }
        }
    }

    private fun loadKitapArsivListe() {
        viewModelScope.launch {
            getKitapArsivListeUseCase().collectLatest { response ->
                _state.update {
                    it.copy(
                        kitapArsivListeSource = response
                    )
                }
            }
        }
    }

    fun kitapArsivle(kitapArsivItem: KitapArsivItem) {
        viewModelScope.launch {
            kitapArsivleUseCase(
                kitapId = kitapArsivItem.kitapId,
                kitapAd = kitapArsivItem.kitapAd,
                yazarAd = kitapArsivItem.yazarAd,
                kitapAciklama = kitapArsivItem.kitapAciklama,
                kitapImageUrl = kitapArsivItem.kitapResim
            ).collectLatest { response ->
                _state.update {
                    it.copy(
                        kitapArsivleSource = response
                    )
                }
            }
        }
    }

    fun setDefaultStateForKitapArsiv() {
        _state.update {
            it.copy(kitapArsivleSource = BaseResourceEvent.Nothing())
        }
    }
}