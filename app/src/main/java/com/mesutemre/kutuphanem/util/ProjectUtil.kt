package com.mesutemre.kutuphanem.util

import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.text.SpannableStringBuilder
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.core.content.FileProvider
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.snackbar.Snackbar
import kotlin.math.roundToInt
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.model.*
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

const val APP_TOKEN_KEY:String = "APP_TOKEN";
const val KULLANICI_ADI_KEY:String = "KULLANICI_ADI";
const val KUTUPHANEM_DB_NAME = "kutuphanem";
const val PARAM_YAYINEVI_DB_KEY:String = "PARAM_YAYINEVI";
const val PARAM_KITAPTUR_DB_KEY:String = "PARAM_KITAPTUR";
const val KULLANICI_DB_MEVCUT:String = "KULLANICI_MEVCUT";
const val CAMERA_REQUEST_CODE:Int = 1991;
const val READ_EXTERNAL_STORAGE_REQUEST_CODE:Int=1992;
const val WRITE_EXTERNAL_STORAGE_REQUEST_CODE:Int=2019;
const val SHARED_PREF_FILE:String = "KUTUPHANEM_SP";

fun showSnackBar(view:View, message:String,@SnackType type: Int){
    var builder:SpannableStringBuilder  = SpannableStringBuilder();
    val sb: Snackbar = Snackbar.make(view,message, Snackbar.LENGTH_SHORT);
    val tv =sb.view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView;
    tv.textSize = 16f;
    tv.typeface = ResourcesCompat.getFont(view.context,R.font.source_sans_pro);
    tv.setTextColor(view.resources.getColor(R.color.whiteTextColor,null))

    when(type){
        SUCCESS->{
            sb.setBackgroundTint(view.resources.getColor(R.color.fistikYesil,null));
        }
        WARNING->{
            sb.setBackgroundTint(view.resources.getColor(R.color.acikTuruncu,null));
        }
        ERROR->{
            sb.setBackgroundTint(view.resources.getColor(R.color.kirmizi,null));
        }

    }
    builder.append(message);

    sb.setText(builder);
    sb.show();
}

fun formatDate(date:Date,pattern:String):String{
    val sdf:SimpleDateFormat = SimpleDateFormat(pattern);
    return sdf.format(date);
}

fun getImageUriFromBitmap(c: Context, inImage:Bitmap):Uri{
    var path:String = "";
    try {
        val bytes: ByteArrayOutputStream = ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        path = MediaStore.Images.Media.insertImage(c.getContentResolver(), inImage, "KUTUPHANEM_IMG_"+System.currentTimeMillis(),null);

    }catch (e:Exception){
        Log.e("Exception",e.localizedMessage);
        e.printStackTrace();
    }
    return Uri.parse(path);
}
fun getPath(context:Context,uri:Uri):String?{
    val isKitKatorAbove = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
    // DocumentProvider
    if (isKitKatorAbove && DocumentsContract.isDocumentUri(context, uri)) {
        // ExternalStorageProvider
        if (isExternalStorageDocument(uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            val split = docId.split(":".toRegex()).toTypedArray()
            val type = split[0]
            if ("primary".equals(type, ignoreCase = true)) {
                return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
            }

        } else if (isDownloadsDocument(uri)) {
            val id = DocumentsContract.getDocumentId(uri)
            val contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id))
            return getStreamFile(context, contentUri)
        } else if (isMediaDocument(uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            val split = docId.split(":".toRegex()).toTypedArray()
            val type = split[0]
            var contentUri: Uri? = null
            if ("image" == type) {
                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            } else if ("video" == type) {
                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            } else if ("audio" == type) {
                contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            }
            return getStreamFile(context, contentUri!!)
        }
    } else if ("content".equals(uri.scheme, ignoreCase = true)) {
        return getStreamFile(context, uri)
    } else if ("file".equals(uri.scheme, ignoreCase = true)) {
        return uri.path
    }
    return null;
}

fun getStreamFile(context: Context,uri:Uri):String?{
    val inputStream = context.contentResolver.openInputStream(uri);
    val createFile = createImageFile();
    copyInputStreamToFile(inputStream!!, createFile);
    inputStream.close();
    return createFile.absolutePath;
}

fun copyInputStreamToFile(`in`: InputStream, file: File) {
    var out: OutputStream? = null
    try {
        out = FileOutputStream(file)
        val buf = ByteArray(1024)
        var len: Int = 0
        while (`in`.read(buf).apply { len = this } > 0) {
            out.write(buf, 0, len)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        try {
            out?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            `in`.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}

@Throws(IOException::class)
private fun createImageFile(): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(Date())
    val imageFileName = "kutsisIMG_" + timeStamp + "_"
    val storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(imageFileName, ".jpg", storageDir)
}

private fun isExternalStorageDocument(uri:Uri):Boolean {
    return "com.android.externalstorage.documents".equals(uri.getAuthority());
}

private fun isDownloadsDocument(uri:Uri):Boolean {
    return "com.android.providers.downloads.documents".equals(uri.getAuthority());
}

private fun isMediaDocument(uri:Uri):Boolean {
    return "com.android.providers.media.documents".equals(uri.getAuthority());
}

@BindingAdapter(value = ["android:rippleEffect"])
fun rippleEffect(view:View,value:Boolean){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val outValue = TypedValue();
        view.context.theme.resolveAttribute(android.R.attr.selectableItemBackground,outValue,true);
        view.foreground = view.context.getDrawable(outValue.resourceId);
    }
}

fun getBitmapFromUrl(url:String): Bitmap? {
    var input: InputStream? = null;
    var bitMap:Bitmap? = null;
    var connection: HttpURLConnection? = null;
    try {
        val url: URL = URL(url);
        connection = url.openConnection() as HttpURLConnection;
        connection.setDoInput(true);
        connection.connect();
        input = connection.inputStream;
        bitMap = BitmapFactory.decodeStream(input);
    }
    catch (e: Exception) {
        e.printStackTrace();
    }
    finally {
        input?.close();
        if(connection != null){
            connection.disconnect();
        }
    }
    return bitMap;
}

/*fun File.deleteDirectory(): Boolean {
    return if (exists()) {
        listFiles()?.forEach {
            if (it.isDirectory) {
                it.deleteDirectory()
            } else {
                it.delete()
            }
        }
        delete()
    } else false
}*/

fun bitmapToFile(bitmap: Bitmap, fileNameToSave: String,requireContext: Context,folderName:String): File? {
    var file: File? = null
    return try {
        file = File(createDownloadedOutputDirectory(requireContext,folderName),fileNameToSave);

        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos) // YOU can also save it in JPEG
        val bitmapdata = bos.toByteArray()

        val fos = FileOutputStream(file)
        fos.write(bitmapdata)
        fos.flush()
        fos.close()
        file
    } catch (e: Exception) {
        e.printStackTrace()
        file // it will return null
    }
}

fun createOutputDirectory(context: Context): File {
    val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
        File(it, context.resources.getString(R.string.app_name)).apply { mkdirs() }
    }
    return if (mediaDir != null && mediaDir.exists())
        mediaDir else context.filesDir
}

fun createDownloadedOutputDirectory(context: Context,folder:String): File {
    val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
        File(it, folder).apply { mkdirs() }
    }
    return if (mediaDir != null && mediaDir.exists())
        mediaDir else context.filesDir
}

fun checkDeviceHasCamera(ctx:Context):Boolean{
    if(ctx.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)){
        return true;
    }
    return false;
}

fun checkDeviceHasFronCamera(ctx:Context):Boolean{
    if(ctx.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)){
        return true;
    }
    return false;
}
fun downloadKitap(kitap:KitapModel, requireContext: Context,isArchive:Boolean):Uri{
    val kitapResim = getBitmapFromUrl(kitap.kitapResimPath!!);
    val bytes: ByteArrayOutputStream = ByteArrayOutputStream();
    if(kitapResim != null){
        kitapResim?.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        var providerAtuh:String = "com.mesutemre.kutuphanem.provider";
        var folderName:String = "Kütüphanem";
        var resimAd:String = kitap.kitapAd+"_"+kitap.kitapId
        if(isArchive){
            resimAd = kitap.kitapId.toString();
        }
        val photoFile = bitmapToFile(kitapResim!!,resimAd+".png",requireContext,folderName);
        val imageUri: Uri = FileProvider.getUriForFile(requireContext,providerAtuh,photoFile!!);
        return imageUri;
    }
    return Uri.EMPTY;
}

inline fun <reified T: Activity>
        Context.startActivity(){
            val intent = Intent(this,T::class.java);
            startActivity(intent);
        }
