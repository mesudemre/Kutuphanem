package com.mesutemre.kutuphanem.viewmodels

import android.app.Application
import android.app.RecoverableSecurityException
import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.base.BaseEvent
import com.mesutemre.kutuphanem.dao.KitapDao
import com.mesutemre.kutuphanem.model.KitapModel
import com.mesutemre.kutuphanem.service.IKitapService
import com.mesutemre.kutuphanem.util.downloadKitap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import java.io.File
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.FileProvider
import com.mesutemre.kutuphanem.util.getPath


@HiltViewModel
class KitapDetayViewModel @Inject
constructor(application: Application,
            private val kitapService: IKitapService,
            private val kitapDao: KitapDao
): AndroidViewModel(application), CoroutineScope {

    private val job = Job();
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main; //Önce işi yap sonra main thread e dön.

    val shareUri = MutableLiveData<BaseEvent<Uri>>();
    val arsivKitap = MutableLiveData<BaseEvent<String>>();
    val kitapResimDownload = MutableLiveData<BaseEvent<Uri>>();
    val kitapArsivMevcut = MutableLiveData<BaseEvent<KitapModel>>();
    val arsivKitapSil = MutableLiveData<BaseEvent<String>>();

    fun prepareShareKitap(kitap:KitapModel, requireContext: Context){
        launch(Dispatchers.IO) {
            val event = BaseEvent(downloadKitap(kitap,requireContext,false));
            event.hasBeenHandled = true;
            if(event.peekContent() == Uri.EMPTY){
                event.hasBeenError = true;
            }
            shareUri.postValue(event);
        }
    }

    fun kitapArsivle(kitap: KitapModel,requireContext: Context){
        viewModelScope.launch {
            async { saveKitapToDB(kitap,requireContext) }
            async { downloadKitapResimForArchive(kitap, requireContext) }
        }
    }

    fun kitapArsivdenCikar(kitap: KitapModel,requireContext: Context){
        viewModelScope.launch {
            async { arsivdenCikar(kitap.kitapId!!,requireContext) }
            async { arsivKitapResimSil(kitap.kitapId,requireContext) }
        }
    }

    private fun saveKitapToDB(kitap:KitapModel,requireContext: Context){
        launch(Dispatchers.IO){
            val event = BaseEvent(requireContext.getString(R.string.kiptaArsivleme));
            kitapDao.kitapKaydet(kitap)
            event.hasBeenHandled = true;
            arsivKitap.postValue(event);
        }
    }

    private fun downloadKitapResimForArchive(kitap:KitapModel,requireContext: Context){
        launch(Dispatchers.IO) {
            val event = BaseEvent(downloadKitap(kitap,requireContext,true));
            event.hasBeenHandled = true;
            if(event.peekContent() == Uri.EMPTY){
                event.hasBeenError = true;
            }
            shareUri.postValue(event);
        }
    }

    private fun arsivdenCikar(kitapId: Int,requireContext: Context){
        launch(Dispatchers.IO){
            val event = BaseEvent(requireContext.getString(R.string.kitapArsivKaldirma));
            kitapDao.kitapSil(kitapId);
            event.hasBeenHandled = true;
            arsivKitapSil.postValue(event);
        }
    }

    private fun arsivKitapResimSil(kitapId: Int?, requireContext: Context) {
        launch(Dispatchers.IO){
            val path:String = Environment.getExternalStorageDirectory().path;
            val f = File(path+"/Android/media/com.mesutemre.kutuphanem/Kütüphanem/${kitapId.toString()}.png");
            if(f.exists()){
                f.delete();
            }
        }
    }

    fun kitapArsivlenmisMi(kitapId:Int){
        launch(Dispatchers.IO){
            val event = BaseEvent(kitapDao.getKitapById(kitapId));
            event.hasBeenHandled = true;
            if(event.peekContent() == null){
                event.hasBeenError = true;
            }
            kitapArsivMevcut.postValue(event);
        }
    }

    override fun onCleared() {
        super.onCleared();
        job.cancel();
    }
}