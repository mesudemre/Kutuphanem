package com.mesutemre.kutuphanem.parametre.ekleme.ui

import androidx.lifecycle.viewModelScope
import com.mesutemre.kutuphanem.base.BaseDataEvent
import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.base.BaseSingleLiveEvent
import com.mesutemre.kutuphanem.base.BaseViewModel
import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem.model.ResponseStatusModel
import com.mesutemre.kutuphanem.parametre.service.IParametreService
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import com.mesutemre.kutuphanem.util.PARAM_KITAPTUR_DB_KEY
import com.mesutemre.kutuphanem.util.PARAM_YAYINEVI_DB_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class ParametreEklemeViewModel @Inject constructor(@IoDispatcher private val ioDispatcher: CoroutineDispatcher,
                                                   private val parametreService: IParametreService
)
    : BaseViewModel() {

    @Inject
    lateinit var customSharedPreferences: CustomSharedPreferences;

    val parametreEklemeResourceEvent = BaseSingleLiveEvent<BaseResourceEvent<ResponseStatusModel>>();

    fun parametreEkle(tip:String?,aciklama:String){
        viewModelScope.launch {
            val jsonObj: JSONObject = JSONObject();
            jsonObj.put("aciklama",aciklama);

            if(tip.equals("kitaptur")){
                kitapTurParametreEkle(jsonObj.toString());
            }else{
                yayinEviParametreEkle(jsonObj.toString());
            }
        }
    }

    private suspend fun kitapTurParametreEkle(json:String){
        parametreEklemeResourceEvent.value = BaseResourceEvent.Loading();
        val kitapTurParametreEklemeResponse = serviceCall(
            call = {
                parametreService.kitapTurKaydet(json)
            },ioDispatcher);

        when(kitapTurParametreEklemeResponse){
            is BaseDataEvent.Success->{
                customSharedPreferences.removeFromSharedPreferences(PARAM_KITAPTUR_DB_KEY);
                parametreEklemeResourceEvent.value = BaseResourceEvent.Success(kitapTurParametreEklemeResponse.data!!);
            }
            is BaseDataEvent.Error->{
                parametreEklemeResourceEvent.value = BaseResourceEvent.Error(kitapTurParametreEklemeResponse.errMessage);
            }
        }
    }

    private suspend fun yayinEviParametreEkle(json:String){
        parametreEklemeResourceEvent.value = BaseResourceEvent.Loading();
        val yayinEviParametreEklemeResponse = serviceCall(
            call = {
                parametreService.yayinEviKaydetGeneric(json)
            },ioDispatcher);

        when(yayinEviParametreEklemeResponse){
            is BaseDataEvent.Success->{
                customSharedPreferences.removeFromSharedPreferences(PARAM_YAYINEVI_DB_KEY);
                parametreEklemeResourceEvent.value = BaseResourceEvent.Success(yayinEviParametreEklemeResponse.data!!);
            }
            is BaseDataEvent.Error->{
                parametreEklemeResourceEvent.value = BaseResourceEvent.Error(yayinEviParametreEklemeResponse.errMessage);
            }
        }
    }
}