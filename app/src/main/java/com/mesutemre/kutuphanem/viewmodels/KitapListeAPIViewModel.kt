package com.mesutemre.kutuphanem.viewmodels

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.base.BaseEvent
import com.mesutemre.kutuphanem.dao.KitapDao
import com.mesutemre.kutuphanem.datasource.KitapListeDataSource
import com.mesutemre.kutuphanem.datasource.KitapListeDataSourceFactory
import com.mesutemre.kutuphanem.model.KitapListeState
import com.mesutemre.kutuphanem.model.KitapModel
import com.mesutemre.kutuphanem.service.IKitapService
import com.mesutemre.kutuphanem.util.downloadKitap
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class KitapListeAPIViewModel  @Inject constructor(application: Application,
                                                  private val kitapDao: KitapDao,
                                                  private val kitapService: IKitapService
): AndroidViewModel(application), CoroutineScope {

    private val disposible = CompositeDisposable();

    var kitapListe:LiveData<PagedList<KitapModel>>;
    private val pageSize = 4;
    private val kitapListeDataSourceFactory:KitapListeDataSourceFactory;

    val shareUri = MutableLiveData<BaseEvent<Uri>>();

    val arsivUri = MutableLiveData<BaseEvent<String>>();
    val arsivKitap = MutableLiveData<BaseEvent<String>>();

    private val job = Job();
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main; //Önce işi yap sonra main thread e dön.

    init {
        kitapListeDataSourceFactory = KitapListeDataSourceFactory(disposible,kitapService);
        val config = PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setInitialLoadSizeHint(pageSize*2)
                .setEnablePlaceholders(false)
                .build();
        kitapListe = LivePagedListBuilder<Int, KitapModel>(kitapListeDataSourceFactory, config).build()
    }

    fun getKitapListeState():LiveData<KitapListeState> = Transformations.switchMap(
            kitapListeDataSourceFactory.kitapListe,
            KitapListeDataSource::kitapState
    )

    fun retry() {
        kitapListeDataSourceFactory.kitapListe.value?.invalidate();
        kitapListeDataSourceFactory.kitapListe.value?.retry();
    }

    fun listIsEmpty(): Boolean {
        return kitapListe.value?.isEmpty() ?: true
    }

    fun prepareShareKitap(kitap:KitapModel, requireContext: Context){
        launch(Dispatchers.IO) {
            val event = BaseEvent(downloadKitap(kitap,requireContext,false));
            event.hasBeenHandled = true;
            if(event.peekContent() == Uri.EMPTY){
                event.hasBeenError = true;
            }
            shareUri.postValue(event);
        }
    }

    fun kitapArsivle(kitap: KitapModel,requireContext: Context){
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

    private fun saveKitapToDB(kitap:KitapModel,requireContext: Context){
        launch(Dispatchers.IO){
            val event = BaseEvent(requireContext.getString(R.string.kiptaArsivleme));
            event.hasBeenHandled = true;
            arsivKitap.postValue(event);
            kitapDao.kitapKaydet(kitap);
        }
    }

    private fun downloadKitapResimForArchive(kitap:KitapModel,requireContext: Context){
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

    override fun onCleared() {
        super.onCleared();
        disposible.clear();
        job.cancel();
    }
}