package com.mesutemre.kutuphanem.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.mesutemre.kutuphanem.model.KitapListeState
import com.mesutemre.kutuphanem.model.KitapModel
import com.mesutemre.kutuphanem.service.IKitapService
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject

class KitapListeDataSource(
        private val kitapService:IKitapService,
        private val disposible: CompositeDisposable
):PageKeyedDataSource<Int,KitapModel>(){

    var kitapState:MutableLiveData<KitapListeState> = MutableLiveData();
    private var retryCompletable: Completable? = null;

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, KitapModel>) {
        val jsonObj: JSONObject = JSONObject();
        jsonObj.put("minKayitNum",0);
        jsonObj.put("maxKayitNum",4);
        disposible.add(
                kitapService.getKitapListe(jsonObj.toString())
                        .subscribe({response->
                            this.updateState(KitapListeState.DONE);
                            callback.onResult(response,null,2);
                        },
                        {
                            this.updateState(KitapListeState.ERROR);
                            setRetry(Action { loadInitial(params, callback) });
                        })
        );
    }

    private fun updateState(state:KitapListeState){
        this.kitapState.postValue(state);
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, KitapModel>) {
        val jsonObj: JSONObject = JSONObject();
        jsonObj.put("minKayitNum",params.key*5);
        jsonObj.put("maxKayitNum",4);

        disposible.add(
                kitapService.getKitapListe(jsonObj.toString())
                        .subscribe({response->
                            this.updateState(KitapListeState.DONE);
                            callback.onResult(response,params.key + 1);
                        },
                        {
                            this.updateState(KitapListeState.ERROR);
                            setRetry(Action { loadAfter(params, callback) });
                        })
        );
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, KitapModel>) {
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }

    fun retry(){
        if (retryCompletable != null) {
            disposible.add(retryCompletable!!
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe())
        }
    }
}