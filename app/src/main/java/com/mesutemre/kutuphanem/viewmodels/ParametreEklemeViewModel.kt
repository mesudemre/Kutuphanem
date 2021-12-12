package com.mesutemre.kutuphanem.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mesutemre.kutuphanem.base.BaseDataEvent
import com.mesutemre.kutuphanem.base.BaseViewModel
import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.model.ResponseStatusModel
import com.mesutemre.kutuphanem.service.IParametreService
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import com.mesutemre.kutuphanem.util.PARAM_KITAPTUR_DB_KEY
import com.mesutemre.kutuphanem.util.PARAM_YAYINEVI_DB_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class ParametreEklemeViewModel @Inject constructor(application: Application,
                                                   private val parametreService: IParametreService)
    : BaseViewModel(application) {

    override val disposible: CompositeDisposable = CompositeDisposable();

    @Inject
    lateinit var customSharedPreferences: CustomSharedPreferences;

    val parametreEklemeResourceEvent = MutableLiveData<BaseResourceEvent<ResponseStatusModel>>();

    fun parametreEkle(tip:String?,aciklama:String){
        val jsonObj: JSONObject = JSONObject();
        jsonObj.put("aciklama",aciklama);

        if(tip.equals("kitaptur")){
            kitapTurParametreEkle(jsonObj.toString());
        }else{
            yayinEviParametreEkle(jsonObj.toString());
        }
    }

    private fun kitapTurParametreEkle(json:String){
        launch(Dispatchers.IO){
            parametreEklemeResourceEvent.postValue(BaseResourceEvent.Loading());
            val kitapTurParametreEklemeResponse = serviceCall(
                call = {
                    parametreService.kitapTurKaydetGeneric(json)
                });

            when(kitapTurParametreEklemeResponse){
                is BaseDataEvent.Success->{
                    customSharedPreferences.removeFromSharedPreferences(PARAM_KITAPTUR_DB_KEY);
                    parametreEklemeResourceEvent.postValue(BaseResourceEvent.Success(kitapTurParametreEklemeResponse.data!!));
                }
                is BaseDataEvent.Error->{
                    parametreEklemeResourceEvent.postValue(BaseResourceEvent.Error(kitapTurParametreEklemeResponse.errMessage));
                }
            }
        }
    }

    private fun yayinEviParametreEkle(json:String){
        launch(Dispatchers.IO){
            parametreEklemeResourceEvent.postValue(BaseResourceEvent.Loading());
            val yayinEviParametreEklemeResponse = serviceCall(
                call = {
                    parametreService.yayinEviKaydetGeneric(json)
                });

            when(yayinEviParametreEklemeResponse){
                is BaseDataEvent.Success->{
                    customSharedPreferences.removeFromSharedPreferences(PARAM_YAYINEVI_DB_KEY);
                    parametreEklemeResourceEvent.postValue(BaseResourceEvent.Success(yayinEviParametreEklemeResponse.data!!));
                }
                is BaseDataEvent.Error->{
                    parametreEklemeResourceEvent.postValue(BaseResourceEvent.Error(yayinEviParametreEklemeResponse.errMessage));
                }
            }
        }
    }
}