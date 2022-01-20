package com.mesutemre.kutuphanem.anasayfa.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.base.BaseDataEvent
import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.base.BaseSingleLiveEvent
import com.mesutemre.kutuphanem.base.BaseViewModel
import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem.exceptions.dao.KutuphanemGlobalExceptionHandlerDao
import com.mesutemre.kutuphanem.kitap.liste.model.KitapModel
import com.mesutemre.kutuphanem.kitap.service.IKitapService
import com.mesutemre.kutuphanem.parametre.kitaptur.model.KitapturModel
import com.mesutemre.kutuphanem.parametre.service.IParametreService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class AnasayfaViewModel @Inject constructor(@IoDispatcher private val ioDispatcher: CoroutineDispatcher,
                                            private val parametreService: IParametreService,
                                            private val kitapService: IKitapService,
                                            private val exceptionHandlerDao: KutuphanemGlobalExceptionHandlerDao,
                                            @ApplicationContext private val appContext: Context

): BaseViewModel() {

    val dashKategoriListeResourceEvent = BaseSingleLiveEvent<BaseResourceEvent<List<KitapturModel>>>();
    val kitapSearchResourceEvent = BaseSingleLiveEvent<BaseResourceEvent<List<KitapModel>>>();

    fun getAnasayfaDashListe(){
        viewModelScope.launch {
            val exList = exceptionHandlerDao.getExceptionList();
            for (e in exList) {
                Log.d("Exception Detay",e.detay!!)
                Log.d("Exception Cihaz",e.cihazInfo);
            }
            async { initDashKategoriListe() }
        }
    }

    private suspend fun initDashKategoriListe(){
        dashKategoriListeResourceEvent.value = BaseResourceEvent.Loading();
        val kitapTurListeResponse = serviceCall(
            call = {
                parametreService.getKitapTurListe()
            },ioDispatcher);
        when(kitapTurListeResponse){
            is BaseDataEvent.Success->{
                dashKategoriListeResourceEvent.value = BaseResourceEvent.Success(kitapTurListeResponse.data!!);
                navigateLiveData.value = R.id.kitapEklemeFragment
            }
            is BaseDataEvent.Error->{
                dashKategoriListeResourceEvent.value = BaseResourceEvent.Error(kitapTurListeResponse.errMessage);
            }
        }
    }

    fun searchKitapYazar(searchText:String){
        viewModelScope.launch {
            kitapSearchResourceEvent.value = BaseResourceEvent.Loading();
            val jsonObj: JSONObject = JSONObject();
            jsonObj.put("kitapAd",searchText);
            jsonObj.put("yazarAd",searchText);
            val kitapListeResponse = serviceCall(
                call = {
                    kitapService.getTumKitapListe(jsonObj.toString());
                },ioDispatcher);

            when(kitapListeResponse){
                is BaseDataEvent.Success->{
                    kitapSearchResourceEvent.value = BaseResourceEvent.Success(kitapListeResponse.data!!);
                }
                is BaseDataEvent.Error->{
                    kitapSearchResourceEvent.value = BaseResourceEvent.Error(kitapListeResponse.errMessage);
                }
            }
        }
    }
}