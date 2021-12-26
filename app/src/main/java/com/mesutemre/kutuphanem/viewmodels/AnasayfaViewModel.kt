package com.mesutemre.kutuphanem.viewmodels

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.mesutemre.kutuphanem.base.BaseDataEvent
import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.base.BaseSingleLiveEvent
import com.mesutemre.kutuphanem.base.BaseViewModel
import com.mesutemre.kutuphanem.model.KitapModel
import com.mesutemre.kutuphanem.model.KitapturModel
import com.mesutemre.kutuphanem.service.IKitapService
import com.mesutemre.kutuphanem.service.IParametreService
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class AnasayfaViewModel @Inject constructor(application: Application,
                                            private val parametreService: IParametreService,
                                            private val kitapService: IKitapService
): BaseViewModel(application) {

    override val disposible: CompositeDisposable = CompositeDisposable();

    val dashKategoriListeResourceEvent = BaseSingleLiveEvent<BaseResourceEvent<List<KitapturModel>>>();
    val kitapSearchResourceEvent = BaseSingleLiveEvent<BaseResourceEvent<List<KitapModel>>>();

    fun getAnasayfaDashListe(){
        viewModelScope.launch {
            async { initDashKategoriListe() }
        }
    }

    private fun initDashKategoriListe(){
        launch(Dispatchers.IO) {
            dashKategoriListeResourceEvent.postValue(BaseResourceEvent.Loading());
            val kitapTurListeResponse = serviceCall(
                call = {
                    parametreService.getKitapTurListe()
                });
            when(kitapTurListeResponse){
                is BaseDataEvent.Success->{
                    dashKategoriListeResourceEvent.postValue(BaseResourceEvent.Success(kitapTurListeResponse.data!!));
                }
                is BaseDataEvent.Error->{
                    dashKategoriListeResourceEvent.postValue(BaseResourceEvent.Error(kitapTurListeResponse.errMessage));
                }
            }
        }
    }

    fun searchKitapYazar(searchText:String){
        launch(Dispatchers.IO) {
            kitapSearchResourceEvent.postValue(BaseResourceEvent.Loading());
            val jsonObj: JSONObject = JSONObject();
            jsonObj.put("kitapAd",searchText);
            jsonObj.put("yazarAd",searchText);

            val kitapListeResponse = serviceCall(
                call = {
                    kitapService.getTumKitapListe(jsonObj.toString());
                });
            when(kitapListeResponse){
                is BaseDataEvent.Success->{
                    kitapSearchResourceEvent.postValue(BaseResourceEvent.Success(kitapListeResponse.data!!));
                }
                is BaseDataEvent.Error->{
                    kitapSearchResourceEvent.postValue(BaseResourceEvent.Error(kitapListeResponse.errMessage));
                }
            }
        }
    }
}