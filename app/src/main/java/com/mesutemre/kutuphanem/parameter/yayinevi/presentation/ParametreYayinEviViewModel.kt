package com.mesutemre.kutuphanem.parameter.yayinevi.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.lifecycle.viewModelScope
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.base.BaseViewModel
import com.mesutemre.kutuphanem.di.DefaultDispatcher
import com.mesutemre.kutuphanem.parameter.yayinevi.domain.model.YayinEviItem
import com.mesutemre.kutuphanem.parameter.yayinevi.domain.use_case.DeleteYayinEviUseCase
import com.mesutemre.kutuphanem.parameter.yayinevi.domain.use_case.GetYayinEviListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 20.05.2022
 */
@HiltViewModel
class ParametreYayinEviViewModel @Inject constructor(
    private val getYayinEviListUseCase: GetYayinEviListUseCase,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val deleteYayinEviUseCase: DeleteYayinEviUseCase
) : BaseViewModel() {

    private val _state = mutableStateOf(ParametreYayinEviState())
    val state: State<ParametreYayinEviState> = _state

    init {
        initYayinEviList(false)
    }

    fun initYayinEviList(isSwipe: Boolean) {
        viewModelScope.launch {
            getYayinEviListUseCase(isSwipe).collect {
                _state.value = _state.value.copy(
                    yayinEviList = it,
                    defaultYayineviList = it.data
                )
            }
        }
    }

    fun onSearchTextChangeValue(text: String) {
        var filterResult: BaseResourceEvent<List<YayinEviItem>> = BaseResourceEvent.Nothing()
        val listToSearch = _state.value.defaultYayineviList

        viewModelScope.launch(defaultDispatcher) {
            if (text.isEmpty()) {
                filterResult = BaseResourceEvent.Success(data = listToSearch!!)
                _state.value = _state.value.copy(
                    yayinEviFilterText = text,
                    yayinEviList = filterResult
                )
                return@launch
            }
            val results = listToSearch?.filter {
                it.aciklama.toUpperCase(Locale.current)
                    .contains(text.trim().toUpperCase(Locale.current), ignoreCase = true)
            }
            if (results.isNullOrEmpty()) {
                filterResult = BaseResourceEvent.Error(
                    messageId = R.string.yayinEviNotFounCriteria,
                    message = null
                )
            } else {
                filterResult = BaseResourceEvent.Success(data = results!!)
            }
            _state.value = _state.value.copy(
                yayinEviFilterText = text,
                yayinEviList = filterResult,
            )
        }
    }

    fun openDeleteConfirmDialog(selectedYayinEvi: YayinEviItem) {
        _state.value = _state.value.copy(
            isPopUpShow = true,
            selectedYayinevi = selectedYayinEvi
        )
    }

    fun onDismissParameterDeleteDialog() {
        _state.value = _state.value.copy(
            isPopUpShow = false,
            selectedYayinevi = null
        )
    }

    fun onClickDeleteYayinevi() {
        viewModelScope.launch {
            deleteYayinEviUseCase(_state.value.selectedYayinevi!!).collect {
                if (it is BaseResourceEvent.Success) {
                    initYayinEviList(true)
                }
                _state.value = _state.value.copy(
                    isPopUpShow = false,
                    yayinEviDelete = it,
                    selectedYayinevi = null
                )
            }
        }
    }
}