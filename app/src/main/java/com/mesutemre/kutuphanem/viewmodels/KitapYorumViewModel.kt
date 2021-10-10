package com.mesutemre.kutuphanem.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mesutemre.kutuphanem.base.BaseEvent
import com.mesutemre.kutuphanem.base.BaseViewModel
import com.mesutemre.kutuphanem.model.KitapYorumModel
import com.mesutemre.kutuphanem.model.ResponseStatusModel
import com.mesutemre.kutuphanem.model.YorumListeModel
import com.mesutemre.kutuphanem.service.IKitapService
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 3.10.2021
 */
@HiltViewModel
class KitapYorumViewModel@Inject
constructor(application: Application,
            private val kitapService: IKitapService
): BaseViewModel(application)  {

    override val disposible: CompositeDisposable = CompositeDisposable();

    val kitapYorumKayit = MutableLiveData<BaseEvent<ResponseStatusModel>>();
    val kitapYorumListe = MutableLiveData<BaseEvent<List<KitapYorumModel>>>();

    fun kitapYorumKaydet(kitapYorum: KitapYorumModel){
        val jsonObj: JSONObject = JSONObject();
        jsonObj.put("yorum",kitapYorum.yorum);

        val jsonKitapObj = JSONObject();
        jsonKitapObj.put("id",kitapYorum.kitap?.kitapId);

        jsonObj.put("kitap",jsonKitapObj);

        disposible.add(
            kitapService.kitapYorumKaydet(jsonObj.toString()) .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ResponseStatusModel>(){
                    override fun onSuccess(response: ResponseStatusModel) {
                        val baseEvent = BaseEvent(response);
                        baseEvent.hasBeenHandled = true;
                        if(!response.statusCode.equals("200")){
                            baseEvent.hasBeenError = true;
                        }
                        kitapYorumKayit.value = baseEvent;
                    }
                    override fun onError(e: Throwable) {
                        val baseEvent = BaseEvent(ResponseStatusModel("500",e.localizedMessage));
                        baseEvent.hasBeenError = true;
                        baseEvent.hasBeenHandled = true;
                        kitapYorumKayit.value = baseEvent;
                    }
                })
        );
    }

    fun getKitapYorumListe(kitapId:Int){
        disposible.add(
          kitapService.getKitapYorumListe(kitapId)
              .subscribeOn(Schedulers.newThread())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribeWith(object : DisposableSingleObserver<YorumListeModel>(){
                  override fun onSuccess(response: YorumListeModel) {
                      val baseEvent = BaseEvent(response.yorumListe);
                      baseEvent.hasBeenHandled = true;
                      kitapYorumListe.value = baseEvent;
                  }

                  override fun onError(e: Throwable) {
                      val baseEvent = BaseEvent(listOf<KitapYorumModel>());
                      baseEvent.hasBeenHandled = true;
                      kitapYorumListe.value = baseEvent;
                  }

              }));
    }
}

