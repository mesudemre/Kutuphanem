package com.mesutemre.kutuphanem.viewmodels

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.mesutemre.kutuphanem.base.BaseViewModel
import com.mesutemre.kutuphanem.dao.KullaniciDao
import com.mesutemre.kutuphanem.model.KitapturModel
import com.mesutemre.kutuphanem.model.Kullanici
import com.mesutemre.kutuphanem.model.KullaniciKitapTurModel
import com.mesutemre.kutuphanem.model.ResponseStatusModel
import com.mesutemre.kutuphanem.repositories.ParametreRepository
import com.mesutemre.kutuphanem.service.IParametreService
import com.mesutemre.kutuphanem.service.KullaniciService
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import com.mesutemre.kutuphanem.util.KULLANICI_ADI_KEY
import com.mesutemre.kutuphanem.util.KULLANICI_DB_MEVCUT
import com.mesutemre.kutuphanem.util.getPath
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfilIslemViewModel @Inject constructor(application: Application,
                                               private val kullaniciService: KullaniciService,
                                               private val kullaniciDao:KullaniciDao,
                                               private val savedStateHandle: SavedStateHandle,
                                               private val parametreService: IParametreService,
                                               private val parametreRepository: ParametreRepository
): BaseViewModel(application) {

    @Inject
    lateinit var customSharedPreferences: CustomSharedPreferences;

    override val disposible: CompositeDisposable = CompositeDisposable();

    val kullanici = MutableLiveData<Kullanici>();
    val kullaniciLoading = MutableLiveData<Boolean>();
    val kullaniciError = MutableLiveData<Boolean>();

    val kullaniciGuncelleLoading = MutableLiveData<Boolean>();
    val kullaniciGuncelleSonuc = MutableLiveData<ResponseStatusModel>();
    val kullaniciGuncelleError = MutableLiveData<Boolean>();

    val kullaniciIlgiAlanlar = MutableLiveData<List<KitapturModel>>();

    fun getKullaniciInfo(){ //Async şekilde türler ve kullanıcı biller alınacak...
        val kullaniciDbMevcut:Boolean = customSharedPreferences.getBooleanFromSharedPreferences(KULLANICI_DB_MEVCUT);
        if(kullaniciDbMevcut){
            val username:String = customSharedPreferences.getStringFromSharedPreferences(KULLANICI_ADI_KEY);
            getKullaniciBilgilerDB(username);
        }else{
            getKullaniciBilgiFromAPI();
        }
    }

    private fun getKullaniciBilgilerDB(kullaniciAd:String){
        launch(Dispatchers.IO){
            kullaniciLoading.postValue(true);
            val user = kullaniciDao.getKullaniciBilgiByUsername(kullaniciAd);
            if(user == null) {
                kullaniciError.postValue(true);
            }else{
                kullanici.postValue(user);
            }
            kullaniciLoading.postValue(false)
        }
    }


    fun getKullaniciIlgiAlanlarFromDB(kullaniciAd: String){
        launch(Dispatchers.IO){
            val kullaniciIlgiAlanListe = kullaniciDao.getKullaniciIlgiAlanListe(kullaniciAd);
            var ilgiAlanListe = mutableListOf<KitapturModel>();
            if(kullaniciIlgiAlanListe != null && kullaniciIlgiAlanListe.size>0){
                for (ia in kullaniciIlgiAlanListe){
                    ilgiAlanListe.add(KitapturModel(ia.aciklamaId,ia.aciklama));
                }
                kullaniciIlgiAlanlar.postValue(ilgiAlanListe);
            }
        }
    }

    private fun getKullaniciBilgiFromAPI(){
        kullaniciLoading.value = true;
        disposible.add(
            kullaniciService.getKullaniciBilgi()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Kullanici>(){
                    override fun onSuccess(t: Kullanici) {
                        kullaniciLoading.value = false;
                        kullaniciError.value = false;
                        kullanici.value = t;
                        async { writeUserToDB(t); }
                        async { writeIlgiAlanlarToDB(t); }
                    }

                    override fun onError(e: Throwable) {
                        kullaniciLoading.value = false;
                        kullaniciError.value = true;
                    }

                }));

    }


    private fun writeUserToDB(kullanici: Kullanici):Unit{
        launch(Dispatchers.IO){
            customSharedPreferences.putBooleanToSharedPreferences(KULLANICI_DB_MEVCUT,true);
            kullaniciDao.kullaniciSil(kullanici.username);
            kullaniciDao.kullaniciKaydet(kullanici);
        }
    }

    private fun writeIlgiAlanlarToDB(kullanici: Kullanici):Unit{
        launch(Dispatchers.IO){
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
}