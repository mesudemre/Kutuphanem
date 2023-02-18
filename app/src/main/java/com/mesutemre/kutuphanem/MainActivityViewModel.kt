package com.mesutemre.kutuphanem

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.viewModelScope
import com.mesutemre.kutuphanem.util.APP_TOKEN_KEY
import com.mesutemre.kutuphanem.util.readString
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import com.mesutemre.kutuphanem_base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 27.02.2022
 */
@HiltViewModel
class MainActivityViewModel @Inject constructor(
    dataStore: DataStore<Preferences>
) : BaseViewModel() {

    private val _mainActivityState = MutableStateFlow(MainActivityState())
    val mainActivityState: StateFlow<MainActivityState> = _mainActivityState

    init {
        viewModelScope.launch {
            val tokenValue = dataStore.readString(APP_TOKEN_KEY).first()
            _mainActivityState.update {
                it.copy(
                    tokenResourceEvent = BaseResourceEvent.Success(tokenValue)
                )
            }
        }
    }

    fun activateMenuAnimation() {
        _mainActivityState.update {
            it.copy(animateMenuVisibility = !it.animateMenuVisibility)
        }
    }

    fun closeFastTransactionMenu() {
        _mainActivityState.update {
            it.copy(animateMenuVisibility = false)
        }
    }
}