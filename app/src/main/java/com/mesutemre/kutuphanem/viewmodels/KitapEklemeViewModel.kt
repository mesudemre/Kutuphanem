package com.mesutemre.kutuphanem.viewmodels

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mesutemre.kutuphanem.R
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
class KitapEklemeViewModel @Inject
constructor(application: Application,
            private val kitapService: IKitapService,
            private val parametreService: IParametreService,
            private val parametreRepository: ParametreRepository
    ): AndroidViewModel(application,), CoroutineScope {

    private val context:Context = application.baseContext;
    private val job = Job();
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main; //Önce işi yap sonra main thread e dön.

    private val disposible = CompositeDisposable();

    @Inject
    lateinit var customSharedPreferences: CustomSharedPreferences;

    val kitapEklemeKitapTurListe = MutableLiveData<List<KitapturModel>>();
    val kitapEklemeKitapTurError = MutableLiveData<Boolean>();
    val kitapEklemeKitapTurLoading = MutableLiveData<Boolean>();

    val kitapEklemeYayinEviListe = MutableLiveData<List<YayineviModel>>();
    val kitapEklemeYayinEviError = MutableLiveData<Boolean>();
    val kitapEklemeYayinEviLoading = MutableLiveData<Boolean>();

    val kitapKaydet = MutableLiveData<Boolean>();
    val kitapKaydetHata = MutableLiveData<Boolean>();
    val kitapKaydetLoading = MutableLiveData<Boolean>();

    val kitapResimYukle = MutableLiveData<ResponseStatusModel>();
    val kitapResimYukleHata = MutableLiveData<Boolean>();
    val kitapResimYukleLoading = MutableLiveData<Boolean>();

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
        disposible.add(
            parametreService.getKitapTurListe()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<KitapturModel>>(){
                    override fun onSuccess(response: List<KitapturModel>) {
                        storeKitapTurInDatabse(response);
                        kitapEklemeKitapTurError.value = false;
                        kitapEklemeKitapTurLoading.value = false;
                        var liste = response.toMutableList();
                        liste.add(KitapturModel(0,context.getString(R.string.kitapTurSecinizText)));
                        val sortedList = liste.sortedWith(compareBy({it.kitapTurId}));
                        kitapEklemeKitapTurListe.value = sortedList;
                    }

                    override fun onError(e: Throwable) {
                        kitapEklemeKitapTurError.value = true;
                        kitapEklemeKitapTurLoading.value = false;
                    }

                }));
    }

    private fun initKitapTurFromDatabase(){
        launch {
            var liste = parametreRepository.getKitapTurListe().toMutableList();
            liste.add(KitapturModel(0,context.getString(R.string.kitapTurSecinizText)));
            val sortedList = liste.sortedWith(compareBy({it.kitapTurId}));
            kitapEklemeKitapTurListe.value = sortedList;
            kitapEklemeKitapTurError.value = false;
            kitapEklemeKitapTurLoading.value = false;
        }
    }

    private fun storeKitapTurInDatabse(kitapTurListe:List<KitapturModel>){
        launch {
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
        disposible.add(
            parametreService.getYayinEviListe()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<YayineviModel>>(){
                    override fun onSuccess(response: List<YayineviModel>) {
                        kitapEklemeYayinEviError.value = false;
                        storeYayinEviInDatabse(response);
                        kitapEklemeYayinEviLoading.value = false;

                        var liste = response.toMutableList();
                        liste.add(YayineviModel(0,context.getString(R.string.yayinEviSecinizText)));
                        val sortedList = liste.sortedWith(compareBy({it.yayinEviId}));
                        kitapEklemeYayinEviListe.value = sortedList;
                    }

                    override fun onError(e: Throwable) {
                        kitapEklemeYayinEviError.value = true;
                        kitapEklemeYayinEviLoading.value = false;
                    }
                }));
    }

    private fun initYayinEviFromDatabase(){
        launch {
            kitapEklemeYayinEviError.value = false;
            kitapEklemeYayinEviLoading.value = false;
            var liste = parametreRepository.getYayinEviListe().toMutableList();
            liste.add(YayineviModel(0,context.getString(R.string.yayinEviSecinizText)));
            val sortedList = liste.sortedWith(compareBy({it.yayinEviId}));
            kitapEklemeYayinEviListe.value = sortedList;
        }
    }

    private fun storeYayinEviInDatabse(yayinEviListe:List<YayineviModel>){
        launch {
            parametreRepository.deleteYayinEviListe();
            parametreRepository.yayinEviParametreKaydet(*yayinEviListe.toTypedArray());
            customSharedPreferences.putBooleanToSharedPreferences(PARAM_YAYINEVI_DB_KEY,true);
        }
    }

    fun kitapKaydet(jsonStr:String,kitapImageUri:Uri,context:Context){
        kitapKaydetLoading.value = true;
        disposible.add(
            kitapService.kitapKaydet(jsonStr) .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ResponseStatusModel>(){
                    override fun onSuccess(response: ResponseStatusModel) {
                       if(response.statusCode.equals("200")){
                           kitapKaydet.value = true;
                           kitapKaydetLoading.value = false;
                           kitapKaydetHata.value = false;
                           val kitapId = response.statusMessage;
                           kitapResimYukle(kitapImageUri,kitapId,context);
                       }else{
                           kitapKaydet.value = false;
                           kitapKaydetLoading.value = false;
                           kitapKaydetHata.value = true;
                       }
                    }
                    override fun onError(e: Throwable) {
                        kitapKaydetHata.value = true;
                        kitapKaydetLoading.value = false;
                    }
                }));
    }

    private fun kitapResimYukle(kitapImageUri: Uri,kitapId:String,context:Context){
        launch {
            val kitapIdParam: RequestBody = RequestBody.create(MediaType.parse("text/plain"),kitapId);
            val originalFile: File =  org.apache.commons.io.FileUtils.getFile(getPath(context,kitapImageUri));
            val photoFile2 = Compressor.compress(context,originalFile){
                default()
                destination(originalFile)
            }
            val fileParam: RequestBody = RequestBody.create(MediaType.parse("image/jpeg"),photoFile2);
            val file: MultipartBody.Part = MultipartBody.Part.createFormData("file",originalFile.name,fileParam);

            kitapResimYukleLoading.value = true;
            disposible.add(
                kitapService.kitapResimYukle(file,kitapIdParam)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<ResponseStatusModel>(){
                        override fun onSuccess(response: ResponseStatusModel) {
                            kitapResimYukle.value = response;
                            kitapResimYukleLoading.value = false;
                            kitapResimYukleHata.value = false;
                            originalFile.delete();
                        }
                        override fun onError(e: Throwable) {
                            kitapResimYukleHata.value = true;
                            kitapResimYukleLoading.value = false;
                        }
                    }));
        }


    }

    override fun onCleared() {
        super.onCleared();
        disposible.clear();
        job.cancel();
    }
}