package com.mesutemre.kutuphanem.viewmodels

import android.app.Application
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mesutemre.kutuphanem.base.BaseViewModel
import com.mesutemre.kutuphanem.datasource.KitapBegeniPagingSource
import com.mesutemre.kutuphanem.model.KitapModel
import com.mesutemre.kutuphanem.service.IKitapService
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 9.09.2021
 */
@HiltViewModel
class KitapBegeniListeViewModel @Inject constructor(application: Application,
                                                    private val kitapService: IKitapService
) : BaseViewModel(application){

    override val disposible: CompositeDisposable = CompositeDisposable();

    fun getKitapBegeniListe():Flow<PagingData<KitapModel>>{
        return Pager(
            config = PagingConfig(pageSize = 5,enablePlaceholders = false),
            pagingSourceFactory = {KitapBegeniPagingSource(kitapService)}
        ).flow
            .cachedIn(viewModelScope);
    }
}