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
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import com.mesutemre.kutuphanem.util.PARAM_KITAPTUR_DB_KEY
import com.mesutemre.kutuphanem.util.PARAM_YAYINEVI_DB_KEY
import com.mesutemre.kutuphanem.util.getPath
import dagger.hilt.android.lifecycle.HiltViewModel
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
): BaseViewModel() {

    @Inject
    lateinit var customSharedPreferences: CustomSharedPreferences;


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

    }

    private suspend fun initKitapTurFromDatabase(){

    }

    private suspend fun yayinEviListeGetir(){
        val fromDb = customSharedPreferences.getBooleanFromSharedPreferences(
            PARAM_YAYINEVI_DB_KEY
        );

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
        /*kitapResimYukleResourceEvent.value = BaseResourceEvent.Loading();
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
        }*/
    }
}