package com.mesutemre.kutuphanem.viewmodels

import android.app.Application
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.base.BaseDataEvent
import com.mesutemre.kutuphanem.base.BaseEvent
import com.mesutemre.kutuphanem.base.BaseViewModel
import com.mesutemre.kutuphanem.dao.KitapDao
import com.mesutemre.kutuphanem.dao.KullaniciDao
import com.mesutemre.kutuphanem.model.KitapModel
import com.mesutemre.kutuphanem.model.Kullanici
import com.mesutemre.kutuphanem.model.ResponseStatusModel
import com.mesutemre.kutuphanem.service.IKitapService
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import com.mesutemre.kutuphanem.util.KULLANICI_ADI_KEY
import com.mesutemre.kutuphanem.util.downloadKitap
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.File
import javax.inject.Inject


@HiltViewModel
class KitapDetayViewModel @Inject
constructor(application: Application,
            private val kitapService: IKitapService,
            private val kitapDao: KitapDao,
            private val kullaniciDao:KullaniciDao
): BaseViewModel(application) {

    val shareUri = MutableLiveData<BaseEvent<Uri>>();
    val shareUriYeni = MutableLiveData<BaseDataEvent<Uri>>();
    val arsivKitap = MutableLiveData<BaseEvent<String>>();
    val kitapResimDownload = MutableLiveData<BaseEvent<Uri>>();
    val kitapArsivMevcut = MutableLiveData<BaseEvent<KitapModel>>();
    val arsivKitapSil = MutableLiveData<BaseEvent<String>>();
    val kitapBegenme = MutableLiveData<BaseEvent<ResponseStatusModel>>();
    val selectedKitap = MutableLiveData<BaseEvent<KitapModel>>();
    val yorumYapanKullanici = MutableLiveData<BaseEvent<Kullanici>>();

    override val disposible: CompositeDisposable = CompositeDisposable();

    @Inject
    lateinit var customSharedPreferences: CustomSharedPreferences;

    fun prepareShareKitap(kitap:KitapModel, requireContext: Context){
        launch(Dispatchers.IO) {
            /*shareUriYeni.postValue(BaseDataEvent.Loading);
            val uri = downloadKitap(kitap,requireContext,false);
            if(uri != Uri.EMPTY){
                shareUriYeni.postValue(BaseDataEvent.Success(uri));
            }else{
                shareUriYeni.postValue(BaseDataEvent.Error(context.getString(R.string.kitapShareError)))
            }
            shareUriYeni.postValue(BaseDataEvent.Handled(true));*/
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

    fun kitapBegenmeIslem(kitapId: Int,begenilmis:Int){
        if(begenilmis>0){
            kitapBegenSil(kitapId);
        }else{
            kitapBegen(kitapId);
        }
    }

    private fun kitapBegen(kitapId: Int){
        val jsonObj: JSONObject = JSONObject();
        jsonObj.put("id",kitapId);
        disposible.add(
            kitapService.kitapBegen(jsonObj.toString()) .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ResponseStatusModel>(){
                    override fun onSuccess(response: ResponseStatusModel) {
                        val baseEvent = BaseEvent(response);
                        baseEvent.hasBeenHandled = true;
                        if(!response.statusCode.equals("200")){
                            baseEvent.hasBeenError = true;
                        }
                        kitapBegenme.value = baseEvent;
                    }
                    override fun onError(e: Throwable) {
                        val baseEvent = BaseEvent(ResponseStatusModel("500",
                            context.getString(R.string.profilGuncellemeSunucuHata)));
                        baseEvent.hasBeenError = true;
                        baseEvent.hasBeenHandled = true;
                        kitapBegenme.value = baseEvent;
                    }
                }));
    }

    private fun kitapBegenSil(kitapId: Int){
        val jsonObj: JSONObject = JSONObject();
        jsonObj.put("id",kitapId);
        disposible.add(
            kitapService.kitapBegenKaldir(jsonObj.toString()) .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ResponseStatusModel>(){
                    override fun onSuccess(response: ResponseStatusModel) {
                        val baseEvent = BaseEvent(response);
                        baseEvent.hasBeenHandled = true;
                        if(!response.statusCode.equals("200")){
                            baseEvent.hasBeenError = true;
                        }
                        kitapBegenme.value = baseEvent;
                    }
                    override fun onError(e: Throwable) {
                        val baseEvent = BaseEvent(ResponseStatusModel("500",
                            context.getString(R.string.profilGuncellemeSunucuHata)));
                        baseEvent.hasBeenError = true;
                        baseEvent.hasBeenHandled = true;
                        kitapBegenme.value = baseEvent;
                    }
                }));
    }

    fun getKitapBilgiler(kitapId: Int){
        launch(Dispatchers.IO) {
            async { getKitapByKitapId(kitapId!!) }
            async { getYorumYapanKullanici()}
        }
    }

    private fun getKitapByKitapId(kitapId: Int){
        val jsonObj: JSONObject = JSONObject();
        jsonObj.put("id",kitapId);
        disposible.add(
            kitapService.getKitapDetay(jsonObj.toString()) .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<KitapModel>(){
                    override fun onSuccess(response: KitapModel) {
                        val baseEvent = BaseEvent(response);
                        baseEvent.hasBeenHandled = true;
                        if(response == null){
                            baseEvent.hasBeenError = true;
                        }
                        selectedKitap.value = baseEvent;
                    }
                    override fun onError(e: Throwable) {
                        val baseEvent = BaseEvent(KitapModel());
                        baseEvent.hasBeenError = true;
                        baseEvent.hasBeenHandled = true;
                        selectedKitap.value = baseEvent;
                    }
                }));
    }

    private suspend fun getYorumYapanKullanici(){
        val kullaniciAd = customSharedPreferences.getStringFromSharedPreferences(KULLANICI_ADI_KEY);
        val kullanici = kullaniciDao.getKullaniciBilgiByUsername(kullaniciAd);
        val baseEvent = BaseEvent(kullanici);
        baseEvent.hasBeenHandled = true;
        if(kullanici == null) {
            baseEvent.hasBeenError = true;
        }
        yorumYapanKullanici.postValue(baseEvent);
    }
}