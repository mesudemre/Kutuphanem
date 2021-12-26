package com.mesutemre.kutuphanem.viewmodels

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.mesutemre.kutuphanem.base.BaseDataEvent
import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.base.BaseSingleLiveEvent
import com.mesutemre.kutuphanem.base.BaseViewModel
import com.mesutemre.kutuphanem.model.KitapturModel
import com.mesutemre.kutuphanem.model.ResponseStatusModel
import com.mesutemre.kutuphanem.model.YayineviModel
import com.mesutemre.kutuphanem.repositories.ParametreRepository
import com.mesutemre.kutuphanem.service.IKitapService
import com.mesutemre.kutuphanem.service.IParametreService
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import com.mesutemre.kutuphanem.util.PARAM_KITAPTUR_DB_KEY
import com.mesutemre.kutuphanem.util.PARAM_YAYINEVI_DB_KEY
import com.mesutemre.kutuphanem.util.getPath
import dagger.hilt.android.lifecycle.HiltViewModel
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.default
import id.zelory.compressor.constraint.destination
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class KitapEklemeViewModel @Inject
constructor(application: Application,
            private val kitapService: IKitapService,
            private val parametreService: IParametreService,
            private val parametreRepository: ParametreRepository
    ): BaseViewModel(application) {

    override val disposible: CompositeDisposable = CompositeDisposable();

    @Inject
    lateinit var customSharedPreferences: CustomSharedPreferences;

    val kitapTurListeResourceEvent = BaseSingleLiveEvent<BaseResourceEvent<List<KitapturModel>>>();
    val yayinEviListeResourceEvent = BaseSingleLiveEvent<BaseResourceEvent<List<YayineviModel>>>();
    val kitapKaydetResourceEvent = BaseSingleLiveEvent<BaseResourceEvent<Boolean>>();
    val kitapResimYukleResourceEvent = BaseSingleLiveEvent<BaseResourceEvent<ResponseStatusModel>>();

    fun initKitapEklemeSpinnerListe(){
        viewModelScope.launch {
            async { kitapTurListeGetir() };
            async { yayinEviListeGetir() };
        }
    }

    private fun kitapTurListeGetir(){
        val fromDb = customSharedPreferences.getBooleanFromSharedPreferences(
            PARAM_KITAPTUR_DB_KEY
        );
        if(fromDb){
            this.initKitapTurFromDatabase();
        }else{
            this.initKitapTurFromService();
        }
    }

    private fun initKitapTurFromService(){
        launch(Dispatchers.IO) {
            kitapTurListeResourceEvent.postValue(BaseResourceEvent.Loading());
            val kitapTurListeResponse = serviceCall(
                call = {
                    parametreService.getKitapTurListe()
                });
            when(kitapTurListeResponse){
                is BaseDataEvent.Success->{
                    kitapTurListeResourceEvent.postValue(BaseResourceEvent.Success(kitapTurListeResponse.data!!));
                    storeKitapTurInDatabse(kitapTurListeResponse.data!!);
                }
                is BaseDataEvent.Error->{
                    kitapTurListeResourceEvent.postValue(BaseResourceEvent.Error(kitapTurListeResponse.errMessage));
                }
            }
        }
    }

    private fun initKitapTurFromDatabase(){
        launch(Dispatchers.IO) {
            kitapTurListeResourceEvent.postValue(BaseResourceEvent.Loading());
            val kitapTurListeDBResponse = dbCall(
                call = {
                    parametreRepository.getKitapTurListe()
                });

            when(kitapTurListeDBResponse){
                is BaseDataEvent.Success->{
                    kitapTurListeResourceEvent.postValue(BaseResourceEvent.Success(kitapTurListeDBResponse.data!!));
                }
                is BaseDataEvent.Error->{
                    kitapTurListeResourceEvent.postValue(BaseResourceEvent.Error(kitapTurListeDBResponse.errMessage));
                }
            }
        }
    }

    private fun storeKitapTurInDatabse(kitapTurListe:List<KitapturModel>){
        launch(Dispatchers.IO) {
            parametreRepository.deleteKitapTurListe();
            parametreRepository.kitapTurParametreKaydet(*kitapTurListe.toTypedArray());
            customSharedPreferences.putBooleanToSharedPreferences(PARAM_KITAPTUR_DB_KEY,true);
        }
    }

    fun yayinEviListeGetir(){
        val fromDb = customSharedPreferences.getBooleanFromSharedPreferences(
            PARAM_YAYINEVI_DB_KEY
        );
        if(fromDb){
            this.initYayinEviFromDatabase();
        }else{
            this.initYayinEviFromService();
        }

    }

    private fun initYayinEviFromService(){
        launch(Dispatchers.IO) {
            yayinEviListeResourceEvent.postValue(BaseResourceEvent.Loading());
            val yayinEviListeResponse = serviceCall(
                call = {
                    parametreService.getYayinEviListe();
                });
            when(yayinEviListeResponse){
                is BaseDataEvent.Success->{
                    yayinEviListeResourceEvent.postValue(BaseResourceEvent.Success(yayinEviListeResponse.data!!));
                    storeYayinEviInDatabse(yayinEviListeResponse.data!!);
                }
                is BaseDataEvent.Error->{
                    yayinEviListeResourceEvent.postValue(BaseResourceEvent.Error(yayinEviListeResponse.errMessage));
                }
            }
        }
    }

    private fun initYayinEviFromDatabase(){
        launch(Dispatchers.IO) {
            yayinEviListeResourceEvent.postValue(BaseResourceEvent.Loading());
            val yayinEviListeDBResponse = dbCall(
                call = {
                    parametreRepository.getYayinEviListe()
                });

            when(yayinEviListeDBResponse){
                is BaseDataEvent.Success->{
                    yayinEviListeResourceEvent.postValue(BaseResourceEvent.Success(yayinEviListeDBResponse.data!!));
                }
                is BaseDataEvent.Error->{
                    yayinEviListeResourceEvent.postValue(BaseResourceEvent.Error(yayinEviListeDBResponse.errMessage));
                }
            }
        }
    }

    private fun storeYayinEviInDatabse(yayinEviListe:List<YayineviModel>){
        launch(Dispatchers.IO) {
            parametreRepository.deleteYayinEviListe();
            parametreRepository.yayinEviParametreKaydet(*yayinEviListe.toTypedArray());
            customSharedPreferences.putBooleanToSharedPreferences(PARAM_YAYINEVI_DB_KEY,true);
        }
    }

    fun kitapKaydet(jsonStr:String,kitapImageUri:Uri,context:Context){
        launch(Dispatchers.IO) {
            kitapKaydetResourceEvent.postValue(BaseResourceEvent.Loading());
            val kitapKaydetResponse = serviceCall(
                call = {
                    kitapService.kitapKaydet(jsonStr)
                });
            when(kitapKaydetResponse){
                is BaseDataEvent.Success->{
                    kitapKaydetResourceEvent.postValue(BaseResourceEvent.Success(true));
                    val kitapId = kitapKaydetResponse.data!!.statusMessage;
                    kitapResimYukle(kitapImageUri,kitapId,context);
                }
                is BaseDataEvent.Error->{
                    kitapKaydetResourceEvent.postValue(BaseResourceEvent.Error(kitapKaydetResponse.errMessage));
                }
            }
        }
    }

    private fun kitapResimYukle(kitapImageUri: Uri,kitapId:String,context:Context){
        launch(Dispatchers.IO) {
            kitapResimYukleResourceEvent.postValue(BaseResourceEvent.Loading());
            val kitapIdParam: RequestBody = RequestBody.create(MediaType.parse("text/plain"),kitapId);
            val originalFile: File =  org.apache.commons.io.FileUtils.getFile(getPath(context,kitapImageUri));
            val photoFile2 = Compressor.compress(context,originalFile){
                default()
                destination(originalFile)
            }
            val fileParam: RequestBody = RequestBody.create(MediaType.parse("image/jpeg"),photoFile2);
            val file: MultipartBody.Part = MultipartBody.Part.createFormData("file",originalFile.name,fileParam);

            val kitapResimYukleRespons = serviceCall(
                call = {
                    kitapService.kitapResimYukle(file,kitapIdParam)
                });
            when(kitapResimYukleRespons){
                is BaseDataEvent.Success->{
                    kitapResimYukleResourceEvent.postValue(BaseResourceEvent.Success(kitapResimYukleRespons.data!!));
                }
                is BaseDataEvent.Error->{
                    kitapResimYukleResourceEvent.postValue(BaseResourceEvent.Error(kitapResimYukleRespons.errMessage));
                }
            }
        }
    }
}