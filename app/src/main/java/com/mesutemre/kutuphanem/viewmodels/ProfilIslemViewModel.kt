package com.mesutemre.kutuphanem.viewmodels

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.mesutemre.kutuphanem.dao.KullaniciDao
import com.mesutemre.kutuphanem.model.KitapturModel
import com.mesutemre.kutuphanem.model.Kullanici
import com.mesutemre.kutuphanem.model.KullaniciKitapTurModel
import com.mesutemre.kutuphanem.model.ResponseStatusModel
import com.mesutemre.kutuphanem.repositories.ParametreRepository
import com.mesutemre.kutuphanem.service.IParametreService
import com.mesutemre.kutuphanem.service.KullaniciService
import com.mesutemre.kutuphanem.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class ProfilIslemViewModel @Inject constructor(application: Application,
                                               private val kullaniciService: KullaniciService,
                                               private val kullaniciDao:KullaniciDao,
                                               private val savedStateHandle: SavedStateHandle,
                                               private val parametreService: IParametreService,
                                               private val parametreRepository: ParametreRepository
): AndroidViewModel(application), CoroutineScope {

    @Inject
    lateinit var customSharedPreferences: CustomSharedPreferences;

    private val job = Job();
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main; //Önce işi yap sonra main thread e dön.

    private val disposible = CompositeDisposable();

    val kullanici = MutableLiveData<Kullanici>();
    val kullaniciLoading = MutableLiveData<Boolean>();
    val kullaniciError = MutableLiveData<Boolean>();
    val kitapTurListe = MutableLiveData<List<KitapturModel>>()
    val kitapTurLoading = MutableLiveData<Boolean>();
    val kitapTurError = MutableLiveData<Boolean>();

    val kullaniciGuncelleLoading = MutableLiveData<Boolean>();
    val kullaniciGuncelleSonuc = MutableLiveData<ResponseStatusModel>();
    val kullaniciGuncelleError = MutableLiveData<Boolean>();

    fun getKullaniciBilgi(){
        val kayitliState: String? = savedStateHandle.get<String>("state");
        val kayitliOlmayanState:String = savedStateHandle.toString();
        if(!kayitliOlmayanState.equals(kayitliState)){
            val kullaniciDbMevcut:Boolean = customSharedPreferences.getBooleanFromSharedPreferences(KULLANICI_DB_MEVCUT);
            val kitapTurDbMevcut :Boolean = customSharedPreferences.getBooleanFromSharedPreferences(PARAM_KITAPTUR_DB_KEY);

            if(kitapTurDbMevcut){
                this.getKitapTurListeFromDB();
            }else{
                this.getKitapTurListeFromAPI();
            }

            if(kullaniciDbMevcut){
                this.getKullaniciBilgiFromDB();
                //this.getKullaniciBilgiFromAPI();
                savedStateHandle.set("state",savedStateHandle.toString());
            }else{
                this.getKullaniciBilgiFromAPI();
                savedStateHandle.set("state",savedStateHandle.toString());
            }
        }
    }

    private fun getKullaniciBilgiFromAPI(){
        kullaniciLoading.value = true;
        launch {
            disposible.add(
                kullaniciService.getKullaniciBilgi()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<Kullanici>(){
                        override fun onSuccess(t: Kullanici) {
                            kullaniciLoading.value = false;
                            kullaniciError.value = false;
                            kullanici.value = t;
                            writeUserToDB(t);
                            writeIlgiAlanlarToDB(t);
                        }

                        override fun onError(e: Throwable) {
                            kullaniciLoading.value = false;
                            kullaniciError.value = true;
                        }

                    }));

        }
    }

    private fun getKullaniciBilgiFromDB(){
        launch {
            val username:String = customSharedPreferences.getStringFromSharedPreferences(KULLANICI_ADI_KEY);
            val kullaniciIlgiAlanListe = kullaniciDao.getKullaniciIlgiAlanListe(username);
            val user = kullaniciDao.getKullaniciBilgiByUsername(username);
            var ilgiAlanListe = ArrayList<KitapturModel>();
            if(kullaniciIlgiAlanListe != null && kullaniciIlgiAlanListe.size>0){
                for (ia in kullaniciIlgiAlanListe){
                    ilgiAlanListe.add(KitapturModel(ia.aciklamaId,ia.aciklama));
                }
                user.ilgiAlanlari = ilgiAlanListe;
            }
            kullanici.value = user;
            kullaniciLoading.value = false;
            kullaniciError.value = false;
        }
    }

    private fun writeUserToDB(kullanici: Kullanici):Unit{
        launch {
            customSharedPreferences.putBooleanToSharedPreferences(KULLANICI_DB_MEVCUT,true);
            kullaniciDao.kullaniciSil(kullanici.username);
            kullaniciDao.kullaniciKaydet(kullanici);
        }
    }

    private fun writeIlgiAlanlarToDB(kullanici: Kullanici):Unit{
        launch {
            kullaniciDao.kullaniciIlgiAlanSil(kullanici.username);
            val ilgiAlanListe = kullanici.ilgiAlanlari;
            if(ilgiAlanListe != null && ilgiAlanListe.size>0){
                for (ia in ilgiAlanListe){
                    var ilgiAlan:KullaniciKitapTurModel =
                        KullaniciKitapTurModel(ia.kitapTurId!!,ia.aciklama!!,kullanici.username);
                    kullaniciDao.kullaniciIlgialanKaydet(ilgiAlan);
                }
            }
        }
    }

    private fun getKitapTurListeFromDB(){
        launch {
            kitapTurListe.value = parametreRepository.getKitapTurListe();
        }
    }

    private fun getKitapTurListeFromAPI(){
        kitapTurLoading.value = true;
        kitapTurError.value = false;
        disposible.add(
            parametreService.getKitapTurListe()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<KitapturModel>>(){
                    override fun onSuccess(response: List<KitapturModel>) {
                        kitapTurListe.value = response;
                        kitapTurLoading.value = false;
                        kitapTurError.value = false;
                    }

                    override fun onError(e: Throwable) {
                        kitapTurLoading.value = false;
                        kitapTurError.value = true;
                    }
                }));
    }

    fun kullaniciBilgiUpdate(jsonStr:String,resimGuncellenecek:Boolean,selectedImageUri:Uri,username:String,context:Context){
        viewModelScope.launch {
            async {kullaniciBilgiGuncelle(jsonStr)};
            async {
                if(resimGuncellenecek){
                    kullaniciResimGuncelle(selectedImageUri,username,context);
                    Glide.get(context).clearMemory();
                }};
            }
        }

    fun kullaniciBilgiGuncelle(jsonStr:String){
            kullaniciGuncelleLoading.value = true;
            disposible.add(
                kullaniciService.kullaniciBilgiGuncelle(jsonStr)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<ResponseStatusModel>(){
                        override fun onSuccess(response: ResponseStatusModel) {
                            kullaniciGuncelleError.value = false;
                            kullaniciGuncelleLoading.value = false;
                            kullaniciGuncelleSonuc.value = response;
                            customSharedPreferences.removeFromSharedPreferences(KULLANICI_DB_MEVCUT);
                        }
                        override fun onError(e: Throwable) {
                            kullaniciGuncelleError.value = true;
                            kullaniciGuncelleLoading.value = false;
                        }
                    }));
    }

    fun kullaniciResimGuncelle(
        selectedImageUri: Uri,
        username: String,
        context: Context
    ){
        kullaniciGuncelleLoading.value = true;
        val usernameParam: RequestBody = RequestBody.create(MediaType.parse("text/plain"),username);
        val originalFile: File =  org.apache.commons.io.FileUtils.getFile(getPath(context,selectedImageUri));
        val fileParam:RequestBody = RequestBody.create(MediaType.parse("image/jpeg"),originalFile);
        val file: MultipartBody.Part = MultipartBody.Part.createFormData("file",originalFile.name,fileParam);
        disposible.add(
            kullaniciService.kullaniciResimGuncelle(file,usernameParam)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ResponseStatusModel>(){
                    override fun onSuccess(response: ResponseStatusModel) {
                        kullaniciGuncelleError.value = false;
                        kullaniciGuncelleLoading.value = false;
                        kullaniciGuncelleSonuc.value = response;
                    }
                    override fun onError(e: Throwable) {
                        kullaniciGuncelleError.value = true;
                        kullaniciGuncelleLoading.value = false;
                    }
                }));
    }

    override fun onCleared() {
        super.onCleared();
        disposible.clear();
        job.cancel();
    }

}