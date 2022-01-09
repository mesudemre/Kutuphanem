package com.mesutemre.kutuphanem.kitap.liste.ui

import android.content.Context
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.base.BaseDataEvent
import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.base.BaseSingleLiveEvent
import com.mesutemre.kutuphanem.base.BaseViewModel
import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem.kitap.dao.KitapDao
import com.mesutemre.kutuphanem.kitap.liste.datasource.KitapBegeniPagingSource
import com.mesutemre.kutuphanem.kitap.liste.datasource.KitapListeApiPagingSource
import com.mesutemre.kutuphanem.kitap.liste.model.KitapModel
import com.mesutemre.kutuphanem.kitap.service.IKitapService
import com.mesutemre.kutuphanem.util.saveFile
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 9.01.2022
 */
@HiltViewModel
class KitapListeViewModel @Inject constructor(@IoDispatcher private val ioDispatcher: CoroutineDispatcher,
                                              @ApplicationContext private val appContext: Context,
                                              private val kitapDao: KitapDao,
                                              private val kitapService: IKitapService
): BaseViewModel() {

    val kitapArsivListeBaseResourceEvent = BaseSingleLiveEvent<BaseResourceEvent<PagingData<KitapModel>>>();
    val shareKitapUri = BaseSingleLiveEvent<BaseResourceEvent<File>>();
    val arsivKitap = BaseSingleLiveEvent<BaseResourceEvent<String>>();
    val kitapArsivlenmisResourceEvent = BaseSingleLiveEvent<BaseResourceEvent<Boolean>>();

    fun getKitapListe(): Flow<PagingData<KitapModel>> {
        return Pager(
            config = PagingConfig(pageSize = 5,enablePlaceholders = false),
            pagingSourceFactory = { KitapListeApiPagingSource(kitapService,appContext) }
        ).flow
            .cachedIn(viewModelScope);
    }

    fun initKitapArsivListe(){
        viewModelScope.launch {
            kitapArsivListeBaseResourceEvent.value = BaseResourceEvent.Loading();
            val items = Pager(
                PagingConfig(
                    pageSize = 4,
                    enablePlaceholders = true,
                )
            ){
                kitapDao.getKitapListe()
            }.flow;

            items.collectLatest {
                kitapArsivListeBaseResourceEvent.value = BaseResourceEvent.Success(it);
            }
        }
    }

    fun getKitapBegeniListe():Flow <PagingData<KitapModel>>{
        return Pager(
            config = PagingConfig(pageSize = 5,enablePlaceholders = false),
            pagingSourceFactory = { KitapBegeniPagingSource(kitapService,appContext) }
        ).flow
            .cachedIn(viewModelScope);
    }

    fun shareKitap(kitap: KitapModel) {
        viewModelScope.launch {
            downloadAndSaveKitap(kitap,false)
        }
    }

    private suspend fun downloadAndSaveKitap(kitap: KitapModel,isArchive:Boolean) {
        shareKitapUri.value = BaseResourceEvent.Loading();
        val downloadKitapResponse = serviceCall(
            call = {
                kitapService.downloadKitapResim(kitap.kitapResimPath!!)
            },ioDispatcher
        );
        when(downloadKitapResponse){
            is BaseDataEvent.Success->{
                val byteArr = downloadKitapResponse.data!!.bytes();
                val kitapSaved = withContext(ioDispatcher) {
                    saveFile(kitap,appContext,isArchive,byteArr);
                }
                if (kitapSaved != null && kitapSaved.exists()) {
                    shareKitapUri.value = BaseResourceEvent.Success(kitapSaved);
                }
            }
            is BaseDataEvent.Error->{
                shareKitapUri.value = BaseResourceEvent.Error(downloadKitapResponse.errMessage);
            }
        }
    }

    fun kitapArsivle(kitap: KitapModel){
        viewModelScope.launch {
            async { saveKitapToDB(kitap) }
            async { saveKitapToLocal(kitap) }
        }
    }

    fun kitapArsivlenmisMi(kitapId:Int){
        viewModelScope.launch {
            kitapArsivlenmisResourceEvent.value = BaseResourceEvent.Loading();
            val kitapDBMevcutResponse = dbCall( call = {
                kitapDao.getKitapById(kitapId)
            },ioDispatcher)
            when(kitapDBMevcutResponse){
                is BaseDataEvent.Success->{
                    kitapArsivlenmisResourceEvent.value = BaseResourceEvent.Success(true);
                }
                is BaseDataEvent.Error->{
                    kitapArsivlenmisResourceEvent.value = BaseResourceEvent.Error(appContext.getString(R.string.kitapDetayHata));
                }
            }
        }
    }

    private suspend fun saveKitapToDB(kitap: KitapModel){
        arsivKitap.value = BaseResourceEvent.Loading();
        val kitapDBKayitResponse = dbCall( call = {
            kitapDao.kitapKaydet(kitap)
        },ioDispatcher)
        when(kitapDBKayitResponse){
            is BaseDataEvent.Success->{
                arsivKitap.value = BaseResourceEvent.Success(appContext.resources.getString(R.string.kiptaArsivleme));
            }
            is BaseDataEvent.Error->{
                arsivKitap.value = BaseResourceEvent.Error(appContext.resources.getString(R.string.kiptaArsivleme))
            }
        }
    }

    private suspend fun saveKitapToLocal(kitap:KitapModel) {
        val downloadKitapResponse = serviceCall(
            call = {
                kitapService.downloadKitapResim(kitap.kitapResimPath!!)
            },ioDispatcher
        );
        when(downloadKitapResponse){
            is BaseDataEvent.Success->{
                val byteArr = downloadKitapResponse.data!!.bytes();
                withContext(ioDispatcher) {
                    saveFile(kitap,appContext,true,byteArr);
                }
            }
        }
    }
}