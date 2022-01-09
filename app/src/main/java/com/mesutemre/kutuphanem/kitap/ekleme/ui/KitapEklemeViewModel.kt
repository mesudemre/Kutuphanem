package com.mesutemre.kutuphanem.kitap.ekleme.ui

import android.content.Context
import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.mesutemre.kutuphanem.base.BaseDataEvent
import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.base.BaseSingleLiveEvent
import com.mesutemre.kutuphanem.base.BaseViewModel
import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem.kitap.service.IKitapService
import com.mesutemre.kutuphanem.model.ResponseStatusModel
import com.mesutemre.kutuphanem.parametre.dao.ParametreRepository
import com.mesutemre.kutuphanem.parametre.kitaptur.model.KitapturModel
import com.mesutemre.kutuphanem.parametre.service.IParametreService
import com.mesutemre.kutuphanem.parametre.yayinevi.model.YayineviModel
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import com.mesutemre.kutuphanem.util.PARAM_KITAPTUR_DB_KEY
import com.mesutemre.kutuphanem.util.PARAM_YAYINEVI_DB_KEY
import com.mesutemre.kutuphanem.util.getPath
import dagger.hilt.android.lifecycle.HiltViewModel
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.default
import id.zelory.compressor.constraint.destination
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class KitapEklemeViewModel @Inject
constructor(@IoDispatcher private val ioDispatcher: CoroutineDispatcher,
            private val kitapService: IKitapService,
            private val parametreService: IParametreService,
            private val parametreRepository: ParametreRepository
): BaseViewModel() {

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

    private suspend fun kitapTurListeGetir(){
        val fromDb = customSharedPreferences.getBooleanFromSharedPreferences(
            PARAM_KITAPTUR_DB_KEY
        );
        if(fromDb){
            this.initKitapTurFromDatabase();
        }else{
            this.initKitapTurFromService();
        }
    }

    private suspend fun initKitapTurFromService(){
        kitapTurListeResourceEvent.value = BaseResourceEvent.Loading();
        val kitapTurListeResponse = serviceCall(
            call = {
                parametreService.getKitapTurListe()
            },ioDispatcher);
        when(kitapTurListeResponse){
            is BaseDataEvent.Success->{
                kitapTurListeResourceEvent.value = BaseResourceEvent.Success(kitapTurListeResponse.data!!);
                storeKitapTurInDatabse(kitapTurListeResponse.data!!);
            }
            is BaseDataEvent.Error->{
                kitapTurListeResourceEvent.value = BaseResourceEvent.Error(kitapTurListeResponse.errMessage);
            }
        }
    }

    private suspend fun initKitapTurFromDatabase(){
        kitapTurListeResourceEvent.value = BaseResourceEvent.Loading();
        val kitapTurListeDBResponse = dbCall(
            call = {
                parametreRepository.getKitapTurListe()
            },ioDispatcher);

        when(kitapTurListeDBResponse){
            is BaseDataEvent.Success->{
                kitapTurListeResourceEvent.value = BaseResourceEvent.Success(kitapTurListeDBResponse.data!!);
            }
            is BaseDataEvent.Error->{
                kitapTurListeResourceEvent.value = BaseResourceEvent.Error(kitapTurListeDBResponse.errMessage);
            }
        }
    }

    private suspend fun storeKitapTurInDatabse(kitapTurListe:List<KitapturModel>){
        withContext(ioDispatcher) {
            parametreRepository.deleteKitapTurListe();
            parametreRepository.kitapTurParametreKaydet(*kitapTurListe.toTypedArray());
            customSharedPreferences.putBooleanToSharedPreferences(PARAM_KITAPTUR_DB_KEY,true);
        }
    }

    private suspend fun yayinEviListeGetir(){
        val fromDb = customSharedPreferences.getBooleanFromSharedPreferences(
            PARAM_YAYINEVI_DB_KEY
        );
        if(fromDb){
            this.initYayinEviFromDatabase();
        }else{
            this.initYayinEviFromService();
        }
    }

    private suspend fun initYayinEviFromService(){
        yayinEviListeResourceEvent.value = BaseResourceEvent.Loading();
        val yayinEviListeResponse = serviceCall(
            call = {
                parametreService.getYayinEviListe();
            },ioDispatcher);
        when(yayinEviListeResponse){
            is BaseDataEvent.Success->{
                yayinEviListeResourceEvent.value = BaseResourceEvent.Success(yayinEviListeResponse.data!!);
                storeYayinEviInDatabse(yayinEviListeResponse.data!!);
            }
            is BaseDataEvent.Error->{
                yayinEviListeResourceEvent.value = BaseResourceEvent.Error(yayinEviListeResponse.errMessage);
            }
        }
    }

    private suspend fun initYayinEviFromDatabase(){
        yayinEviListeResourceEvent.value = BaseResourceEvent.Loading();
        val yayinEviListeDBResponse = dbCall(
            call = {
                parametreRepository.getYayinEviListe()
            },ioDispatcher);

        when(yayinEviListeDBResponse){
            is BaseDataEvent.Success->{
                yayinEviListeResourceEvent.value = BaseResourceEvent.Success(yayinEviListeDBResponse.data!!);
            }
            is BaseDataEvent.Error->{
                yayinEviListeResourceEvent.value = BaseResourceEvent.Error(yayinEviListeDBResponse.errMessage);
            }
        }
    }

    private suspend fun storeYayinEviInDatabse(yayinEviListe:List<YayineviModel>){
        withContext(ioDispatcher) {
            parametreRepository.deleteYayinEviListe();
            parametreRepository.yayinEviParametreKaydet(*yayinEviListe.toTypedArray());
            customSharedPreferences.putBooleanToSharedPreferences(PARAM_YAYINEVI_DB_KEY,true);
        }
    }

     fun kitapKaydet(jsonStr:String,kitapImageUri:Uri,context:Context){
        viewModelScope.launch {
            kitapKaydetResourceEvent.value = BaseResourceEvent.Loading();
            val kitapKaydetResponse = serviceCall(
                call = {
                    kitapService.kitapKaydet(jsonStr)
                },ioDispatcher);
            when(kitapKaydetResponse){
                is BaseDataEvent.Success->{
                    kitapKaydetResourceEvent.value = BaseResourceEvent.Success(true);
                    val kitapId = kitapKaydetResponse.data!!.statusMessage;
                    kitapResimYukle(kitapImageUri,kitapId,context);
                }
                is BaseDataEvent.Error->{
                    kitapKaydetResourceEvent.value = BaseResourceEvent.Error(kitapKaydetResponse.errMessage);
                }
            }
        }
    }

    private suspend fun kitapResimYukle(kitapImageUri: Uri,kitapId:String,context:Context){
        kitapResimYukleResourceEvent.value = BaseResourceEvent.Loading();
        val kitapIdParam: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),kitapId);
        val originalFile: File =  org.apache.commons.io.FileUtils.getFile(getPath(context,kitapImageUri));
        val photoFile2 = Compressor.compress(context,originalFile){
            default()
            destination(originalFile)
        }
        val fileParam: RequestBody = RequestBody.create("image/jpeg".toMediaTypeOrNull(),photoFile2);
        val file: MultipartBody.Part = MultipartBody.Part.createFormData("file",originalFile.name,fileParam);

        val kitapResimYukleResponse = serviceCall(
            call = {
                kitapService.kitapResimYukle(file,kitapIdParam)
            },ioDispatcher);
        when(kitapResimYukleResponse){
            is BaseDataEvent.Success->{
                kitapResimYukleResourceEvent.value = BaseResourceEvent.Success(kitapResimYukleResponse.data!!);
            }
            is BaseDataEvent.Error->{
                kitapResimYukleResourceEvent.value = BaseResourceEvent.Error(kitapResimYukleResponse.errMessage);
            }
        }
    }
}