package com.mesutemre.kutuphanem.parameter.kitaptur.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.lifecycle.viewModelScope
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.base.BaseViewModel
import com.mesutemre.kutuphanem.di.DefaultDispatcher
import com.mesutemre.kutuphanem.parameter.kitaptur.domain.model.KitapTurItem
import com.mesutemre.kutuphanem.parameter.kitaptur.domain.use_case.DeleteKitapTurUseCase
import com.mesutemre.kutuphanem.parameter.kitaptur.domain.use_case.GetKitapTurListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 3.07.2022
 */
@HiltViewModel
class ParametreKitapTurViewModel @Inject constructor(
    private val getKitapTurListUseCase: GetKitapTurListUseCase,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val deleteKitapTurUseCase: DeleteKitapTurUseCase
): BaseViewModel() {

    private val _state = mutableStateOf(ParametreKitapTurState())
    val state: State<ParametreKitapTurState> = _state

    init {
        initKitapTurList(false)
    }

    fun initKitapTurList(isSwipe: Boolean) {
        viewModelScope.launch {
            getKitapTurListUseCase(isSwipe).collect {
                _state.value = _state.value.copy(
                    kitapTurList = it,
                    defaultKitapTurList = it.data
                )
            }
        }
    }

    fun onSearchTextChangeValue(text: String) {
        var filterResult: BaseResourceEvent<List<KitapTurItem>> = BaseResourceEvent.Nothing()
        val listToSearch = _state.value.defaultKitapTurList

        viewModelScope.launch(defaultDispatcher) {
            if (text.isEmpty()) {
                filterResult = BaseResourceEvent.Success(data = listToSearch!!)
                _state.value = _state.value.copy(
                    kitapTurFilterText = text,
                    kitapTurList = filterResult
                )
                return@launch
            }
            val results = listToSearch?.filter {
                it.aciklama.toUpperCase(Locale.current)
                    .contains(text.trim().toUpperCase(Locale.current), ignoreCase = true)
            }
            if (results.isNullOrEmpty()) {
                filterResult = BaseResourceEvent.Error(
                    messageId = R.string.kitapTurNotFounCriteria,
                    message = null
                )
            } else {
                filterResult = BaseResourceEvent.Success(data = results!!)
            }
            _state.value = _state.value.copy(
                kitapTurFilterText = text,
                kitapTurList = filterResult,
            )
        }
    }

    fun openDeleteConfirmDialog(selectedKitapTur: KitapTurItem) {
        _state.value = _state.value.copy(
            isPopUpShow = true,
            selectedKitapTur = selectedKitapTur
        )
    }

    fun onDismissParameterDeleteDialog() {
        _state.value = _state.value.copy(
            isPopUpShow = false,
            selectedKitapTur = null
        )
    }

    fun onClickDeleteKitapTur() {
        viewModelScope.launch {
            deleteKitapTurUseCase(_state.value.selectedKitapTur!!).collect {
                if (it is BaseResourceEvent.Success) {
                    initKitapTurList(true)
                }
                _state.value = _state.value.copy(
                    isPopUpShow = false,
                    kitapTurDelete = it,
                    selectedKitapTur = null
                )
            }
        }
    }
}