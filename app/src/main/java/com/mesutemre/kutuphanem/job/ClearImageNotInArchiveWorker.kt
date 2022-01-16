package com.mesutemre.kutuphanem.job

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem.kitap.dao.KitapDao
import com.mesutemre.kutuphanem.kitap.liste.model.KitapModel
import com.mesutemre.kutuphanem.util.arsivResimSil
import com.mesutemre.kutuphanem.util.showKutuphanemBasicNotification
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.File

/**
 * @Author: mesutemre.celenk
 * @Date: 11.01.2022
 */
@HiltWorker
class ClearImageNotInArchiveWorker @AssistedInject constructor(
    @Assisted val appContext: Context,
    @Assisted val workerParams: WorkerParameters,
    private val kitapDao: KitapDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return withContext(ioDispatcher) {
            val readExternalStorage = ContextCompat.checkSelfPermission(appContext, Manifest.permission.READ_EXTERNAL_STORAGE);
            if (readExternalStorage == PackageManager.PERMISSION_GRANTED) {
                val resimListe = getResimListe();
                if (resimListe != null) {
                    val kitapListe = kitapDao.getArsivKitapListe();
                    if (kitapListe == null || kitapListe.size == 0) {
                        tumResimSil(resimListe);
                    }else {
                        sartliResimSil(resimListe,kitapListe);
                    }
                }
            }
            Result.success();
        }
    }

    private fun sartliResimSil(resimListe: MutableList<String>, kitapListe: MutableList<KitapModel>) {
        val resimSilinmesin: (MutableList<KitapModel>, Int) -> Boolean = { liste, kitapId ->
            liste.any {
                it.kitapId == kitapId
            }
        }
        kitapListe.forEach {
            if(resimSilinmesin(kitapListe,it.kitapId!!)) {
                val index = resimListe.indexOf(it.kitapId!!.toString())
                resimListe.removeAt(index)
            }
        }
        resimListe.forEach {
            appContext.arsivResimSil(it);
        }
        appContext.showKutuphanemBasicNotification(appContext.getString(R.string.app_name),
            appContext.getString(R.string.clear_arsiv_image_result));
    }

    private fun tumResimSil(resimListe: List<String>) {
        for (resim in resimListe) {
            appContext.arsivResimSil(resim);
        }
        appContext.showKutuphanemBasicNotification(appContext.getString(R.string.app_name),
            appContext.getString(R.string.clear_arsiv_image_result));
    }

    private fun getResimListe():MutableList<String> {
        val isNumeric: (String) -> Boolean = { str ->
            str?.toIntOrNull()?.let { true } ?: false
        }
        val list = mutableListOf<String>();

        val directory = File(appContext.filesDir,"arsiv");
        if (directory.exists()) {
            val files = directory.listFiles();
            files.forEach {
                if (isNumeric(it.nameWithoutExtension)) {
                    list.add(it.nameWithoutExtension);
                }
            }
        }
        return list;
    }
}