package com.mesutemre.kutuphanem.kitap.liste.ui

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.base.BaseEvent
import com.mesutemre.kutuphanem.base.BaseViewModel
import com.mesutemre.kutuphanem.kitap.dao.KitapDao
import com.mesutemre.kutuphanem.kitap.liste.datasource.KitapListeApiPagingSource
import com.mesutemre.kutuphanem.kitap.liste.model.KitapModel
import com.mesutemre.kutuphanem.kitap.service.IKitapService
import com.mesutemre.kutuphanem.util.downloadKitap
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KitapListeAPIViewModel  @Inject constructor(application: Application,
                                                  private val kitapDao: KitapDao,
                                                  private val kitapService: IKitapService
): BaseViewModel(application) {

    override val disposible: CompositeDisposable = CompositeDisposable();

    val shareUri = MutableLiveData<BaseEvent<Uri>>();
    val arsivUri = MutableLiveData<BaseEvent<String>>();
    val arsivKitap = MutableLiveData<BaseEvent<String>>();

    fun getKitapListe(): Flow<PagingData<KitapModel>> {
        return Pager(
            config = PagingConfig(pageSize = 5,enablePlaceholders = false),
            pagingSourceFactory = { KitapListeApiPagingSource(kitapService,context) }
        ).flow
            .cachedIn(viewModelScope);
    }

    fun prepareShareKitap(kitap: KitapModel, requireContext: Context){
        launch(Dispatchers.IO) {
            val event = BaseEvent(downloadKitap(kitap,requireContext,false));
            event.hasBeenHandled = true;
            if(event.peekContent() == Uri.EMPTY){
                event.hasBeenError = true;
            }
            shareUri.postValue(event);
        }
    }

    fun kitapArsivle(kitap: KitapModel, requireContext: Context){
        viewModelScope.launch {
            if(!isKitapArsivMevcut(kitap.kitapId!!)){
                async { saveKitapToDB(kitap,requireContext) }
                async { downloadKitapResimForArchive(kitap, requireContext) }
            }else{
                val event = BaseEvent(requireContext.getString(R.string.kitapArsivdeMevcut));
                event.hasBeenError = true;
                event.hasBeenHandled = true;
                arsivKitap.postValue(event);
            }
        }
    }

    private fun saveKitapToDB(kitap: KitapModel, requireContext: Context){
        launch(Dispatchers.IO){
            val event = BaseEvent(requireContext.getString(R.string.kiptaArsivleme));
            event.hasBeenHandled = true;
            arsivKitap.postValue(event);
            kitapDao.kitapKaydet(kitap);
        }
    }

    private fun downloadKitapResimForArchive(kitap: KitapModel, requireContext: Context){
        launch(Dispatchers.IO) {
            if(!isKitapArsivMevcut(kitap.kitapId!!)){
                val uri:Uri = downloadKitap(kitap,requireContext,true);
                var event = BaseEvent("");
                if(uri == Uri.EMPTY){
                    event.hasBeenError = true;
                    event = BaseEvent(requireContext.getString(R.string.kitapArsivResimHata));
                }
                arsivUri.postValue(event);
                event.hasBeenHandled = true;
            }
        }
    }

    suspend fun isKitapArsivMevcut(kitapId:Int):Boolean{
        val kitap = kitapDao.getKitapById(kitapId);
        if(kitap != null){
            return true;
        }
        return false;
    }

}