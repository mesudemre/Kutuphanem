package com.mesutemre.kutuphanem.kitap.detay.ui

import android.content.Context
import androidx.lifecycle.viewModelScope
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
import com.mesutemre.kutuphanem.kitap.dao.KitapDao
import com.mesutemre.kutuphanem.kitap.liste.model.KitapModel
import com.mesutemre.kutuphanem.kitap.service.IKitapService
import com.mesutemre.kutuphanem.kitap.yorum.model.KitapPuanModel
import com.mesutemre.kutuphanem.kitap.yorum.model.KitapYorumModel
import com.mesutemre.kutuphanem.model.ResponseStatusModel
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import com.mesutemre.kutuphanem.util.KULLANICI_ADI_KEY
import com.mesutemre.kutuphanem.util.KULLANICI_DB_MEVCUT
import com.mesutemre.kutuphanem.util.saveFile
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.File
import javax.inject.Inject

@HiltViewModel
class KitapDetayViewModel @Inject
constructor(
    private val kitapService: IKitapService,
    private val kitapDao: KitapDao,
    private val kullaniciDao: KullaniciDao,
    private val kullaniciService: KullaniciService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @ApplicationContext private val appContext: Context
): BaseViewModel() {

    val shareKitapUri = BaseSingleLiveEvent<BaseResourceEvent<File>>()
    val arsivKitap = BaseSingleLiveEvent<BaseResourceEvent<String>>()
    val arsivKitapSil = BaseSingleLiveEvent<BaseResourceEvent<String>>()
    val kitapArsivMevcut = BaseSingleLiveEvent<BaseResourceEvent<KitapModel>>()
    val kitapBegenme = BaseSingleLiveEvent<BaseResourceEvent<ResponseStatusModel>>()
    val selectedKitap = BaseSingleLiveEvent<BaseResourceEvent<KitapModel>>()
    val yorumYapanKullanici = BaseSingleLiveEvent<BaseResourceEvent<Kullanici>>()
    val kitapYorumKayit = BaseSingleLiveEvent<BaseResourceEvent<ResponseStatusModel>>()
    val kitapYorumListe = BaseSingleLiveEvent<BaseResourceEvent<List<KitapYorumModel>>>()
    val kitapPuanKayit = BaseSingleLiveEvent<BaseResourceEvent<ResponseStatusModel>>()

    val kitapPdfFoo = BaseSingleLiveEvent<BaseResourceEvent<ByteArray>>()

    @Inject
    lateinit var customSharedPreferences: CustomSharedPreferences

    fun prepareShareKitap(kitap: KitapModel){
        viewModelScope.launch {
            downloadAndSaveKitap(kitap,false)
        }
    }

    private suspend fun downloadAndSaveKitap(kitap: KitapModel,isArchive:Boolean) {
        shareKitapUri.value = BaseResourceEvent.Loading()
        val downloadKitapResponse = serviceCall(
            call = {
                kitapService.downloadKitapResim(kitap.kitapResimPath!!)
            },ioDispatcher
        )
        when(downloadKitapResponse){
            is BaseDataEvent.Success->{
                val byteArr = downloadKitapResponse.data!!.bytes()
                val kitapSaved = withContext(ioDispatcher) {
                    saveFile(kitap,appContext,isArchive,byteArr)
                }
                if (kitapSaved != null && kitapSaved.exists()) {
                    shareKitapUri.value = BaseResourceEvent.Success(kitapSaved)
                }
            }
            is BaseDataEvent.Error->{
                shareKitapUri.value = BaseResourceEvent.Error(downloadKitapResponse.errMessage)
            }
        }
    }

    fun kitapArsivle(kitap: KitapModel){
        viewModelScope.launch {
            async { saveKitapToDB(kitap) }
            async { saveKitapToLocal(kitap) }
        }
    }

    private suspend fun saveKitapToLocal(kitap:KitapModel) {
        val downloadKitapResponse = serviceCall(
            call = {
                kitapService.downloadKitapResim(kitap.kitapResimPath!!)
            },ioDispatcher
        )
        when(downloadKitapResponse){
            is BaseDataEvent.Success->{
                val byteArr = downloadKitapResponse.data!!.bytes()
                withContext(ioDispatcher) {
                    saveFile(kitap,appContext,true,byteArr)
                }
            }
        }
    }

    fun kitapArsivdenCikar(kitap: KitapModel){
        viewModelScope.launch {
            arsivdenCikar(kitap.kitapId!!)
        }
    }

    private suspend fun saveKitapToDB(kitap: KitapModel){
        arsivKitap.value = BaseResourceEvent.Loading()
        val kitapDBKayitResponse = dbCall( call = {
            kitapDao.kitapKaydet(kitap)
        },ioDispatcher)
        when(kitapDBKayitResponse){
            is BaseDataEvent.Success->{
                arsivKitap.value = BaseResourceEvent.Success(appContext.resources.getString(R.string.kiptaArsivleme))
            }
            is BaseDataEvent.Error->{
                arsivKitap.value = BaseResourceEvent.Error(appContext.resources.getString(R.string.kiptaArsivleme))
            }
        }
    }

    private suspend fun arsivdenCikar(kitapId: Int){
        arsivKitapSil.value = BaseResourceEvent.Loading()
        val kitapDBSilResponse = dbCall( call = {
            kitapDao.kitapSil(kitapId)
        },ioDispatcher)
        when(kitapDBSilResponse){
            is BaseDataEvent.Success->{
                arsivKitapSil.value = BaseResourceEvent.Success(appContext.resources.getString(R.string.kitapArsivKaldirma))
            }
            is BaseDataEvent.Error->{
                arsivKitapSil.value = BaseResourceEvent.Error(appContext.resources.getString(R.string.kitapArsivKaldirma))
            }
        }
    }

    fun kitapArsivlenmisMi(kitapId:Int){
        viewModelScope.launch {
            kitapArsivMevcut.value = BaseResourceEvent.Loading()
            val kitapDBMevcutResponse = dbCall( call = {
                kitapDao.getKitapById(kitapId)
            },ioDispatcher)
            when(kitapDBMevcutResponse){
                is BaseDataEvent.Success->{
                    kitapArsivMevcut.value = BaseResourceEvent.Success(kitapDBMevcutResponse.data!!)
                }
                is BaseDataEvent.Error->{
                    kitapArsivMevcut.value = BaseResourceEvent.Error(appContext.resources.getString(R.string.kitapArsivKaldirma))
                }
            }
        }
    }

    fun kitapBegenmeIslem(kitapId: Int,begenilmis:Int){
        viewModelScope.launch {
            if(begenilmis>0){
                kitapBegenSil(kitapId)
            }else{
                kitapBegen(kitapId)
            }
        }
    }

    private suspend fun kitapBegen(kitapId: Int){
        val jsonObj: JSONObject = JSONObject()
        jsonObj.put("id",kitapId)
        kitapBegenme.value = BaseResourceEvent.Loading()
        val kitapBegenResponse = serviceCall(
            call = {
                kitapService.kitapBegen(jsonObj.toString())
            },ioDispatcher
        )
        when(kitapBegenResponse){
            is BaseDataEvent.Success->{
                kitapBegenme.value = BaseResourceEvent.Success(kitapBegenResponse.data!!)
            }
            is BaseDataEvent.Error->{
                kitapBegenme.value = BaseResourceEvent.Error(kitapBegenResponse.errMessage)
            }
        }
    }

    private suspend fun kitapBegenSil(kitapId: Int){
        val jsonObj: JSONObject = JSONObject()
        jsonObj.put("id",kitapId)
        kitapBegenme.value = BaseResourceEvent.Loading()
        val kitapBegenResponse = serviceCall(
            call = {
                kitapService.kitapBegenKaldir(jsonObj.toString())
            },ioDispatcher
        )
        when(kitapBegenResponse){
            is BaseDataEvent.Success->{
                kitapBegenme.value = BaseResourceEvent.Success(kitapBegenResponse.data!!)
            }
            is BaseDataEvent.Error->{
                kitapBegenme.value = BaseResourceEvent.Error(kitapBegenResponse.errMessage)
            }
        }
    }

    fun getKitapBilgiler(kitapId: Int){
        viewModelScope.launch {
            async { getKitapByKitapId(kitapId!!) }
            async { getYorumYapanKullanici()}
        }
    }

    private suspend fun getKitapByKitapId(kitapId: Int){
        selectedKitap.value = BaseResourceEvent.Loading()
        val jsonObj: JSONObject = JSONObject()
        jsonObj.put("id",kitapId)
        val kitapDetayResponse = serviceCall(
            call = {
                kitapService.getKitapDetay(jsonObj.toString())
            },ioDispatcher
        )
        when(kitapDetayResponse){
            is BaseDataEvent.Success->{
                selectedKitap.value = BaseResourceEvent.Success(kitapDetayResponse.data!!)
            }
            is BaseDataEvent.Error->{
                selectedKitap.value = BaseResourceEvent.Error(kitapDetayResponse.errMessage)
            }
        }
    }

    private suspend fun getYorumYapanKullanici(){
        yorumYapanKullanici.value = BaseResourceEvent.Loading()
        val kullaniciAd = customSharedPreferences.getStringFromSharedPreferences(KULLANICI_ADI_KEY)
        val kullaniciDbMevcut:Boolean = customSharedPreferences.getBooleanFromSharedPreferences(KULLANICI_DB_MEVCUT)
        if (kullaniciDbMevcut) {
            getYorumYapanKullaniciFromDB(kullaniciAd)
        }else {
            getYorumYapanKullaniciFromService()
        }
    }

    private suspend fun getYorumYapanKullaniciFromDB(kullaniciAd:String) {
        val kullaniciBilgiResponse = dbCall( call = {
            kullaniciDao.getKullaniciBilgiByUsername(kullaniciAd)
        },ioDispatcher)

        when (kullaniciBilgiResponse) {
            is BaseDataEvent.Success->{
                yorumYapanKullanici.value = BaseResourceEvent.Success(kullaniciBilgiResponse.data!!)
            }
            is BaseDataEvent.Error->{
                yorumYapanKullanici.value = BaseResourceEvent.Error(appContext.getString(R.string.profilSayfaHata))
            }
        }
    }

    private suspend fun getYorumYapanKullaniciFromService() {
        val kullaniciResponse = serviceCall(
            call = {
                kullaniciService.getKullaniciBilgi()
            },ioDispatcher)
        when(kullaniciResponse){
            is BaseDataEvent.Success->{
                yorumYapanKullanici.value = BaseResourceEvent.Success(kullaniciResponse.data!!)
                writeUserToDB(kullaniciResponse.data)
                writeIlgiAlanlarToDB(kullaniciResponse.data)
            }
            is BaseDataEvent.Error->{
                yorumYapanKullanici.value = BaseResourceEvent.Error(kullaniciResponse.errMessage)
            }
        }
    }

    private suspend fun writeUserToDB(kullanici: Kullanici) {
        withContext(ioDispatcher){
            customSharedPreferences.putToSharedPref(KULLANICI_DB_MEVCUT,true)
            kullaniciDao.kullaniciSil(kullanici.username)
            kullaniciDao.kullaniciKaydet(kullanici)
        }
    }

    private suspend fun writeIlgiAlanlarToDB(kullanici: Kullanici) {
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

    fun kitapYorumKaydet(kitapYorum: KitapYorumModel){
        viewModelScope.launch {
            kitapYorumKayit.value = BaseResourceEvent.Loading()
            val jsonObj: JSONObject = JSONObject()
            jsonObj.put("yorum",kitapYorum.yorum)

            val jsonKitapObj = JSONObject()
            jsonKitapObj.put("id",kitapYorum.kitap?.kitapId)

            jsonObj.put("kitap",jsonKitapObj)

            val kitapYorumResponse = serviceCall(
                call = {
                    kitapService.kitapYorumKaydet(jsonObj.toString())
                },ioDispatcher
            )
            when(kitapYorumResponse){
                is BaseDataEvent.Success->{
                    kitapYorumKayit.value = BaseResourceEvent.Success(kitapYorumResponse.data!!)
                }
                is BaseDataEvent.Error->{
                    kitapYorumKayit.value = BaseResourceEvent.Error(kitapYorumResponse.errMessage)
                }
            }
        }
    }

    fun getKitapYorumListe(kitapId:Int){
        viewModelScope.launch {
            kitapYorumListe.value = BaseResourceEvent.Loading()
            val kitapYorumListeResponse = serviceCall(
                call = {
                    kitapService.getKitapYorumListe(kitapId)
                },ioDispatcher
            )
            when(kitapYorumListeResponse){
                is BaseDataEvent.Success->{
                    kitapYorumListe.value = BaseResourceEvent.Success(kitapYorumListeResponse.data!!.yorumListe)
                }
                is BaseDataEvent.Error->{
                    kitapYorumListe.value = BaseResourceEvent.Error(kitapYorumListeResponse.errMessage)
                }
            }
        }
    }

    fun kitapPuanKaydet(kitapPuanModel: KitapPuanModel){
        viewModelScope.launch {
            kitapPuanKayit.value = BaseResourceEvent.Loading()
            val jsonObj: JSONObject = JSONObject()
            jsonObj.put("puan",kitapPuanModel.puan)

            val jsonKitapObj = JSONObject()
            jsonKitapObj.put("id",kitapPuanModel.kitap.kitapId)
            jsonKitapObj.put("kitapPuan",kitapPuanModel.kitap.kitapPuan)

            jsonObj.put("kitap",jsonKitapObj)

            val kitapPuanKaydetResponse = serviceCall(
                call = {
                    kitapService.kitapPuanKaydet(jsonObj.toString())
                },ioDispatcher
            )

            when(kitapPuanKaydetResponse){
                is BaseDataEvent.Success->{
                    kitapPuanKayit.value = BaseResourceEvent.Success(kitapPuanKaydetResponse.data!!)
                }
                is BaseDataEvent.Error->{
                    kitapPuanKayit.value = BaseResourceEvent.Error(kitapPuanKaydetResponse.errMessage)
                }
            }
        }
    }

    fun downloadKitapPdf() {
        viewModelScope.launch {
            kitapPdfFoo.value = BaseResourceEvent.Loading()
            val downloadKitapFooResponse = serviceCall(
                call = {
                    kitapService.downloadKitapFoo("http://192.168.1.105:8080/KutuphaneSistemiWS/api/kitap/indir")
                },ioDispatcher
            )
            when(downloadKitapFooResponse){
                is BaseDataEvent.Success->{
                    val byteArr = downloadKitapFooResponse.data!!.bytes()
                    //kitapPdfFoo.value = BaseResourceEvent.Success(Uri.parse(String(byteArr,Charsets.UTF_8)))
                    kitapPdfFoo.value = BaseResourceEvent.Success(byteArr)
                    /*val kitapSaved = withContext(ioDispatcher) {
                        downloadKitap(appContext,byteArr)
                    }
                    if (kitapSaved != null && kitapSaved.exists()) {
                        kitapPdfFoo.value = BaseResourceEvent.Success(kitapSaved)
                    }*/
                }
                is BaseDataEvent.Error->{
                    kitapPdfFoo.value = BaseResourceEvent.Error(downloadKitapFooResponse.errMessage)
                }
            }
        }
    }
}