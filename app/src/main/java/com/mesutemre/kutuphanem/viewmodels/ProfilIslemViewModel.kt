package com.mesutemre.kutuphanem.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.mesutemre.kutuphanem.dao.KullaniciDao
import com.mesutemre.kutuphanem.model.KitapturModel
import com.mesutemre.kutuphanem.model.Kullanici
import com.mesutemre.kutuphanem.model.KullaniciKitapTurModel
import com.mesutemre.kutuphanem.repositories.ParametreRepository
import com.mesutemre.kutuphanem.service.IParametreService
import com.mesutemre.kutuphanem.service.KullaniciService
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import com.mesutemre.kutuphanem.util.KULLANICI_ADI_KEY
import com.mesutemre.kutuphanem.util.KULLANICI_DB_MEVCUT
import com.mesutemre.kutuphanem.util.PARAM_KITAPTUR_DB_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
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

    override fun onCleared() {
        super.onCleared();
        disposible.clear();
        job.cancel();
    }

}