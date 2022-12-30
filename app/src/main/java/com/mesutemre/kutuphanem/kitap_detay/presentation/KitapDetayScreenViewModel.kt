package com.mesutemre.kutuphanem.kitap_detay.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.mesutemre.kutuphanem.kitap_detay.domain.model.KitapDetayBottomSheetState
import com.mesutemre.kutuphanem.kitap_detay.domain.model.KitapDetayBottomsheetYorumListModel
import com.mesutemre.kutuphanem.kitap_detay.domain.model.KitapDetayBottomsheetYorumListType
import com.mesutemre.kutuphanem.kitap_detay.domain.model.KitapYorumKaydetModel
import com.mesutemre.kutuphanem.kitap_detay.domain.use_case.GetKitapDetayByIdUseCase
import com.mesutemre.kutuphanem.kitap_detay.domain.use_case.GetKitapYorumListeByKitapIdUseCase
import com.mesutemre.kutuphanem.kitap_detay.domain.use_case.GetUserInfoFromDataStore
import com.mesutemre.kutuphanem.kitap_detay.domain.use_case.KitapYorumKaydetUseCase
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
class KitapDetayScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getKitapDetayByIdUseCase: GetKitapDetayByIdUseCase,
    private val getKitapYorumListeByKitapIdUseCase: GetKitapYorumListeByKitapIdUseCase,
    private val getUserInfoFromDataStore: GetUserInfoFromDataStore,
    private val kitapYorumKaydetUseCase: KitapYorumKaydetUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(KitapDetayScreenState())
    val state: StateFlow<KitapDetayScreenState> = _state

    val kitapId = savedStateHandle.get<Int>("kitapId")
    val isFromArsiv = savedStateHandle.get<Boolean>("isArsiv")

    init {
        _state.update {
            it.copy(
                kitapId = this.kitapId ?: 0,
                isFromArsiv = this.isFromArsiv ?: false
            )
        }
        setUserInfo()
        getKitapDetayInfo()
    }

    private fun getKitapDetayInfo() {
        viewModelScope.launch {
            getKitapDetayByIdUseCase(
                _state.value.kitapId,
                _state.value.isFromArsiv
            ).collectLatest { response ->
                _state.update {
                    it.copy(
                        kitapDetayItemResource = response
                    )
                }
            }
        }
    }

    fun onExpandKitapDetayBottomSheet(kitapDetayAciklama: String) {
        _state.update {
            it.copy(
                kitapDetayAciklama = kitapDetayAciklama,
                kitapDetayBottomSheetState = KitapDetayBottomSheetState.KITAP_DETAY_ACIKLAMA
            )
        }
    }

    fun onExpandYorumBottomSheet() {
        _state.update {
            it.copy(
                kitapDetayBottomSheetState = KitapDetayBottomSheetState.YORUM
            )
        }
    }

    fun getKitapYorumListe(isSwipe: Boolean) {
        if (isSwipe || state.value.yorumListeModel.isNullOrEmpty()) {
            getYorumListe()
        }
    }

    private fun getYorumListe() {
        viewModelScope.launch {
            var kitapDetayBottomsheetYorumList: MutableList<KitapDetayBottomsheetYorumListModel> =
                mutableListOf(
                    KitapDetayBottomsheetYorumListModel(
                        type = KitapDetayBottomsheetYorumListType.YAZMA_ITEM
                    ),
                    KitapDetayBottomsheetYorumListModel(
                        type = KitapDetayBottomsheetYorumListType.YASAL_UYARI_ITEM
                    )
                )
            kitapId?.let { kitapId ->
                getKitapYorumListeByKitapIdUseCase(kitapId).collectLatest { response ->
                    if (response is BaseResourceEvent.Success) {
                        response.data?.forEach { yorum ->
                            kitapDetayBottomsheetYorumList.add(
                                KitapDetayBottomsheetYorumListModel(
                                    type = KitapDetayBottomsheetYorumListType.YORUM_ITEM,
                                    data = yorum
                                )
                            )
                        }
                    }
                    _state.update {
                        it.copy(
                            yorumListeModel = kitapDetayBottomsheetYorumList,
                            kitapYorumListeResouce = response
                        )
                    }
                }
            }
        }
    }

    fun getDefaultBottomsheetYorumListe() = listOf<KitapDetayBottomsheetYorumListModel>(
        KitapDetayBottomsheetYorumListModel(
            type = KitapDetayBottomsheetYorumListType.YAZMA_ITEM
        ),
        KitapDetayBottomsheetYorumListModel(
            type = KitapDetayBottomsheetYorumListType.YASAL_UYARI_ITEM
        )
    )

    fun onChangeYorumText(text: String) {
        _state.update {
            it.copy(
                yorumText = text
            )
        }
    }

    fun setUserInfo() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    userInfo = getUserInfoFromDataStore()
                )
            }
        }
    }

    fun kitapYorumKaydet(
        yorumText: String
    ) {
        kitapId?.let { id ->
            viewModelScope.launch {
                kitapYorumKaydetUseCase(
                    KitapYorumKaydetModel(
                        yorumText = yorumText,
                        kitapId = id
                    )
                ).collectLatest { response ->
                    if (response is BaseResourceEvent.Success) {
                        getKitapYorumListe(true)
                    }
                    _state.update {
                        it.copy(
                            kitapYorumKaydetResource = response,
                            yorumText = ""
                        )
                    }
                }
            }
        }
    }
}