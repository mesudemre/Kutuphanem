package com.mesutemre.kutuphanem.auth.profil.ui

import android.content.Context
import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.auth.dao.KullaniciDao
import com.mesutemre.kutuphanem.auth.profil.model.Kullanici
import com.mesutemre.kutuphanem.auth.profil.model.KullaniciKitapTurModel
import com.mesutemre.kutuphanem.auth.service.KullaniciService
import com.mesutemre.kutuphanem.base.BaseDataEvent
import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.base.BaseSingleLiveEvent
import com.mesutemre.kutuphanem.base.BaseViewModel
import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem.model.ResponseStatusModel
import com.mesutemre.kutuphanem.parametre.kitaptur.model.KitapturModel
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import com.mesutemre.kutuphanem.util.KULLANICI_ADI_KEY
import com.mesutemre.kutuphanem.util.KULLANICI_DB_MEVCUT
import com.mesutemre.kutuphanem.util.getPath
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
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
class ProfilIslemViewModel @Inject constructor(@IoDispatcher private val ioDispatcher: CoroutineDispatcher,
                                               @ApplicationContext private val appContext: Context,
                                               private val kullaniciService: KullaniciService,
                                               private val kullaniciDao: KullaniciDao,
): BaseViewModel() {

    @Inject
    lateinit var customSharedPreferences: CustomSharedPreferences

    val kullaniciBilgiResourceEvent = BaseSingleLiveEvent<BaseResourceEvent<Kullanici>>()
    val kullaniciIlgiAlanlar = BaseSingleLiveEvent<BaseResourceEvent<List<KitapturModel>>>()
    val kullaniciBilgiGuncelleResourceEvent = BaseSingleLiveEvent<BaseResourceEvent<ResponseStatusModel>>()

    fun getKullaniciInfo(){ //Async şekilde türler ve kullanıcı biller alınacak...
        viewModelScope.launch {
            val kullaniciDbMevcut:Boolean = customSharedPreferences.getBooleanFromSharedPreferences(KULLANICI_DB_MEVCUT)
            if(kullaniciDbMevcut){
                val username:String = customSharedPreferences.getStringFromSharedPreferences(KULLANICI_ADI_KEY)
                getKullaniciBilgilerDB(username)
            }else{
                getKullaniciBilgiFromAPI()
            }
        }
    }

    private suspend fun getKullaniciBilgilerDB(kullaniciAd:String){
        kullaniciBilgiResourceEvent.value = BaseResourceEvent.Loading()
        val userResponse = dbCall( call = {
            kullaniciDao.getKullaniciBilgiByUsername(kullaniciAd)
        },ioDispatcher)
        when(userResponse){
            is BaseDataEvent.Success->{
                kullaniciBilgiResourceEvent.value = BaseResourceEvent.Success(userResponse.data!!)
            }
            is BaseDataEvent.Error->{
                kullaniciBilgiResourceEvent.value = BaseResourceEvent.Error(appContext.resources.getString(R.string.profilGuncellemeSunucuHata))
            }
        }
    }

    fun getKullaniciIlgiAlanlarFromDB(kullaniciAd: String){
        viewModelScope.launch {
            kullaniciIlgiAlanlar.value = BaseResourceEvent.Loading()
            val kullaniciIlgiAlanlariResponse = dbCall( call = {
                kullaniciDao.getKullaniciIlgiAlanListe(kullaniciAd)
            },ioDispatcher)
            when(kullaniciIlgiAlanlariResponse) {
                is BaseDataEvent.Success->{
                    var ilgiAlanListe = mutableListOf<KitapturModel>()
                    if(kullaniciIlgiAlanlariResponse.data != null && kullaniciIlgiAlanlariResponse.data.size>0){
                        for (ia in kullaniciIlgiAlanlariResponse.data){
                            ilgiAlanListe.add(KitapturModel(ia.aciklamaId,ia.aciklama))
                        }
                    }
                    kullaniciIlgiAlanlar.value = BaseResourceEvent.Success(ilgiAlanListe)
                }
                is BaseDataEvent.Error->{
                    kullaniciIlgiAlanlar.value = BaseResourceEvent.Error(appContext.resources.getString(R.string.profilGuncellemeSunucuHata))
                }
            }
        }
    }

    private suspend fun getKullaniciBilgiFromAPI(){
        kullaniciBilgiResourceEvent.value = BaseResourceEvent.Loading()
        val kullaniciResponse = serviceCall(
            call = {
                kullaniciService.getKullaniciBilgi()
            },ioDispatcher)
        when(kullaniciResponse){
            is BaseDataEvent.Success->{
                kullaniciBilgiResourceEvent.value = BaseResourceEvent.Success(kullaniciResponse.data!!)
                writeUserToDB(kullaniciResponse.data!!)
                writeIlgiAlanlarToDB(kullaniciResponse.data!!)
            }
            is BaseDataEvent.Error->{
                kullaniciBilgiResourceEvent.value = BaseResourceEvent.Error(kullaniciResponse.errMessage)
            }
        }
    }

    private suspend fun writeUserToDB(kullanici: Kullanici):Unit{
        withContext(ioDispatcher){
            customSharedPreferences.putToSharedPref(KULLANICI_DB_MEVCUT,true)
            kullaniciDao.kullaniciSil(kullanici.username)
            kullaniciDao.kullaniciKaydet(kullanici)
        }
    }

    private suspend fun writeIlgiAlanlarToDB(kullanici: Kullanici):Unit{
        withContext(ioDispatcher){
            kullaniciDao.kullaniciIlgiAlanSil(kullanici.username)
            val ilgiAlanListe = kullanici.ilgiAlanlari
            if(ilgiAlanListe != null && ilgiAlanListe.size>0){
                for (ia in ilgiAlanListe){
                    var ilgiAlan: KullaniciKitapTurModel =
                        KullaniciKitapTurModel(ia.kitapTurId!!,ia.aciklama!!,kullanici.username)
                    kullaniciDao.kullaniciIlgialanKaydet(ilgiAlan)
                }
            }
        }
    }

    fun kullaniciBilgiUpdate(jsonStr:String,resimGuncellenecek:Boolean,selectedImageUri:Uri,username:String,context:Context){
        viewModelScope.launch {
            async {kullaniciBilgiGuncelle(jsonStr)}
            async {
                if(resimGuncellenecek){
                    kullaniciResimGuncelle(selectedImageUri,username)
                    Glide.get(context).clearMemory()
                }}
            }
        }

    fun kullaniciBilgiGuncelle(jsonStr:String){
        viewModelScope.launch {
            kullaniciBilgiGuncelleResourceEvent.value = BaseResourceEvent.Loading()
            val kullaniciBilgiGuncelleResponse = serviceCall(
                call = {
                    kullaniciService.kullaniciBilgiGuncelle(jsonStr)
                },ioDispatcher)

            when(kullaniciBilgiGuncelleResponse){
                is BaseDataEvent.Success->{
                    customSharedPreferences.removeFromSharedPreferences(KULLANICI_DB_MEVCUT)
                    kullaniciBilgiGuncelleResourceEvent.value = BaseResourceEvent.Success(kullaniciBilgiGuncelleResponse.data!!)
                }
                is BaseDataEvent.Error->{
                    kullaniciBilgiGuncelleResourceEvent.value = BaseResourceEvent.Error(kullaniciBilgiGuncelleResponse.errMessage)
                }
            }
        }
    }

    fun kullaniciResimGuncelle(
        selectedImageUri: Uri,
        username: String
    ){
        viewModelScope.launch {
            val usernameParam: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),username)
            val originalFile: File =  org.apache.commons.io.FileUtils.getFile(getPath(appContext,selectedImageUri))
            val fileParam:RequestBody = RequestBody.create("image/jpeg".toMediaTypeOrNull(),originalFile)
            val file: MultipartBody.Part = MultipartBody.Part.createFormData("file",originalFile.name,fileParam)

            kullaniciBilgiGuncelleResourceEvent.value = BaseResourceEvent.Loading()
            val kullaniciResimGuncelleResponse = serviceCall(
                call = {
                    kullaniciService.kullaniciResimGuncelle(file,usernameParam)
                },ioDispatcher)

            when(kullaniciResimGuncelleResponse){
                is BaseDataEvent.Success->{
                    kullaniciBilgiGuncelleResourceEvent.value = BaseResourceEvent.Success(kullaniciResimGuncelleResponse.data!!)
                }
                is BaseDataEvent.Error->{
                    kullaniciBilgiGuncelleResourceEvent.value = BaseResourceEvent.Error(kullaniciResimGuncelleResponse.errMessage)
                }
            }
        }
    }
}