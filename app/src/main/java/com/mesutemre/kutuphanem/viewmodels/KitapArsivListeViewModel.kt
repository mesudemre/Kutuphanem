package com.mesutemre.kutuphanem.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mesutemre.kutuphanem.base.BaseEvent
import com.mesutemre.kutuphanem.dao.KitapDao
import com.mesutemre.kutuphanem.model.KitapModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * @Author: mesutemre.celenk
 * @Date: 7.09.2021
 */
@HiltViewModel
class KitapArsivListeViewModel@Inject
    constructor(application: Application,
            private val kitapDao: KitapDao
): AndroidViewModel(application), CoroutineScope {

    private val job = Job();
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main; //Önce işi yap sonra main thread e dön.

    val kitapListe = MutableLiveData<BaseEvent<PagingData<KitapModel>>>();

    fun initKitapArsivListe(){
        launch(Dispatchers.IO){
            val items = Pager(
                PagingConfig(
                    pageSize = 4,
                    enablePlaceholders = true,
                )
            ){
                kitapDao.getKitapListe()
            }.flow;
            items.collectLatest {
                val event = BaseEvent(it);
                event.hasBeenHandled = false;
                kitapListe.postValue(event);
            }
        }
    }
}