package com.mesutemre.kutuphanem.profile.presentation

import androidx.lifecycle.viewModelScope
import com.mesutemre.kutuphanem.profile.domain.use_case.GetKullaniciBilgi
import com.mesutemre.kutuphanem_base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val getKullaniciBilgi: GetKullaniciBilgi
) : BaseViewModel() {

    private val _state = MutableStateFlow(ProfileScreenState())
    val state: StateFlow<ProfileScreenState> = _state

    init {
        loadKullaniciBilgi()
    }

    private fun loadKullaniciBilgi() {
        viewModelScope.launch {
            getKullaniciBilgi().collectLatest { response ->
                _state.update {
                    it.copy(
                        kullaniciBilgiResourceEvent = response
                    )
                }
            }
        }
    }
}