package com.mesutemre.kutuphanem.viewmodels

import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.dao.KitapDao
import com.mesutemre.kutuphanem.model.KitapModel
import com.mesutemre.kutuphanem.service.IKitapService
import com.mesutemre.kutuphanem.util.bitmapToFile
import com.mesutemre.kutuphanem.util.getBitmapFromUrl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class KitapDetayViewModel @Inject
constructor(application: Application,
            private val kitapService: IKitapService,
            private val kitapDao: KitapDao
): AndroidViewModel(application,), CoroutineScope {

    private val job = Job();
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main; //Önce işi yap sonra main thread e dön.

    val shareIntent = MutableLiveData<Intent>();

    fun prepareShareKitap(share:Intent,kitap:KitapModel,requireContext: Context){
        launch(Dispatchers.IO) {
            share.setType("image/png");
            share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            val kitapResim = getBitmapFromUrl(kitap.kitapResimPath!!);
            val bytes: ByteArrayOutputStream = ByteArrayOutputStream();
            kitapResim!!.compress(Bitmap.CompressFormat.PNG, 100, bytes);
            val photoFile = bitmapToFile(kitapResim!!,kitap.kitapAd+"_"+kitap.kitapId+".png",requireContext);
            val imageUri: Uri = FileProvider.getUriForFile(requireContext,"com.mesutemre.kutuphanem.provider",photoFile!!);
            share.putExtra(Intent.EXTRA_TEXT,kitap.kitapAd+" "+requireContext.resources.getString(R.string.paylasimKitapAdText));
            share.putExtra(Intent.EXTRA_STREAM, imageUri);

            shareIntent.postValue(share);
        }
    }

    override fun onCleared() {
        super.onCleared();
        job.cancel();
    }
}