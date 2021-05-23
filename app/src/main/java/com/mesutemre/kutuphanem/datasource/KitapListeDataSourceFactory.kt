package com.mesutemre.kutuphanem.datasource

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.mesutemre.kutuphanem.model.KitapModel
import com.mesutemre.kutuphanem.service.IKitapService
import io.reactivex.disposables.CompositeDisposable

class KitapListeDataSourceFactory(
        private val disposable: CompositeDisposable,
        private val kitapService:IKitapService
): DataSource.Factory<Int, KitapModel>() {

    val kitapListe = MutableLiveData<KitapListeDataSource>();

    override fun create(): DataSource<Int, KitapModel> {
        val kitapListeDataSource = KitapListeDataSource(kitapService,disposable);
        kitapListe.postValue(kitapListeDataSource);
        return kitapListeDataSource;
    }


}