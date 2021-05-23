package com.mesutemre.kutuphanem.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mesutemre.kutuphanem.datasource.KitapListeDataSource
import com.mesutemre.kutuphanem.datasource.KitapListeDataSourceFactory
import com.mesutemre.kutuphanem.model.KitapListeState
import com.mesutemre.kutuphanem.model.KitapModel
import com.mesutemre.kutuphanem.service.IKitapService
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class KitapListeViewModel  @Inject constructor(application: Application,
                                                 private val kitapService: IKitapService
): AndroidViewModel(application), CoroutineScope {

    private val disposible = CompositeDisposable();

    var kitapListe:LiveData<PagedList<KitapModel>>;
    private val pageSize = 4;
    private val kitapListeDataSourceFactory:KitapListeDataSourceFactory;

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

    override fun onCleared() {
        super.onCleared();
        disposible.clear();
        job.cancel();
    }
}