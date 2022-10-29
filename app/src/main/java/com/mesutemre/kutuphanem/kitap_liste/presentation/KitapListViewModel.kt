package com.mesutemre.kutuphanem.kitap_liste.presentation

import com.mesutemre.kutuphanem.kitap_liste.domain.model.SelectedListType
import com.mesutemre.kutuphanem_base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class KitapListViewModel @Inject constructor(

) : BaseViewModel() {

    private val _state = MutableStateFlow(KitapListState())
    val state: StateFlow<KitapListState> = _state

    init {

    }

    fun setSelectedListType(listType: SelectedListType) {
        _state.update {
            it.copy(
                selectedListType = listType
            )
        }
    }

}