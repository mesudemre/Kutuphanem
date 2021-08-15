package com.mesutemre.kutuphanem.util

import android.content.ContentUris
import android.content.Context
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
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.listener.TextInputErrorClearListener
import com.mesutemre.kutuphanem.model.SnackTypeEnum
import kotlinx.android.synthetic.main.resim_sec_bottom_sheet_dialog_fragment.*
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

const val APP_TOKEN_KEY:String = "APP_TOKEN";
const val KULLANICI_ADI_KEY:String = "KULLANICI_ADI";
const val API_URL:String = "http://192.168.1.104:8080/KutuphaneSistemiWS/";
const val KUTUPHANEM_DB_NAME = "kutuphanem";
const val PARAM_YAYINEVI_DB_KEY:String = "PARAM_YAYINEVI";
const val PARAM_KITAPTUR_DB_KEY:String = "PARAM_KITAPTUR";
const val KULLANICI_DB_MEVCUT:String = "KULLANICI_MEVCUT";
const val CAMERA_REQUEST_CODE:Int = 1991;
const val READ_EXTERNAL_STORAGE_REQUEST_CODE:Int=1992;
const val WRITE_EXTERNAL_STORAGE_REQUEST_CODE:Int=2019;
const val SHARED_PREF_FILE:String = "KUTUPHANEM_SP";

fun ImageView.getImageFromUrl(url:String?, iv: ImageView){
    val circularProgressDrawable = CircularProgressDrawable(iv.context);
    circularProgressDrawable.strokeWidth = 8f
    circularProgressDrawable.centerRadius = 40f
    circularProgressDrawable.start()
    val options = RequestOptions()
        .placeholder(circularProgressDrawable)
        .error(R.mipmap.ic_launcher);
    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this);
}

fun ImageView.getCircleImageFromUrl(url:String?, iv: ImageView){
    val circularProgressDrawable = CircularProgressDrawable(iv.context);
    circularProgressDrawable.strokeWidth = 8f
    circularProgressDrawable.centerRadius = 40f
    circularProgressDrawable.start()
    val options = RequestOptions()
        .placeholder(circularProgressDrawable)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .skipMemoryCache(true)
        .error(R.mipmap.ic_launcher);
    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .circleCrop()
        .into(this);
}

fun ImageView.getCircleImageFromBitmap(photo:Bitmap?,iv:ImageView){
    Glide.with(iv.context)
        .asBitmap()
        .load(photo)
        .circleCrop()
        .into(iv);
}

fun ImageView.getCircleImageFromUri(photo:Uri?,iv:ImageView){
    Glide.with(iv.context)
        .load(photo)
        .circleCrop()
        .into(iv);
}

fun ImageView.getCircleImageFromResource(resourceId:Int?,iv:ImageView){
    Glide.with(iv.context)
        .load(resourceId)
        .circleCrop()
        .into(iv);
}

fun TextInputLayout.writeFormatDate(date:Date?,component:TextInputLayout){
    val sdf: SimpleDateFormat = SimpleDateFormat("dd.MM.yyyy");
    if(date != null){
        component.editText?.setText(sdf.format(date));
    }else{
        component.editText?.setText("01.01.2021");
    }
}

@BindingAdapter(value = ["android:downloadUrl"])
fun downloadImage(view:ImageView,url:String?){
    view.getImageFromUrl(url,view);
}

@BindingAdapter(value = ["android:circleDownloadUrl"])
fun downloadCirclerImage(view:ImageView,url:String?){
    view.getCircleImageFromUrl(url,view);
}

@BindingAdapter(value = ["android:bitMapImageLoad"])
fun loadCircleBitmapImageLoad(view:ImageView,photo:Bitmap?){
    view.getCircleImageFromBitmap(photo,view);
}

@BindingAdapter(value = ["android:circleImageLoad"])
fun loadCircleImage(view:ImageView,url:String?){
    view.getCircleImageFromUrl(url,view);
}

@BindingAdapter(value = ["android:writeDateStringValue"])
fun writeDateStrValue(view:TextInputLayout,date:Date?){
    view.writeFormatDate(date,view);
}

@BindingAdapter(value = ["android:clearStringValidation"])
fun clearStringValidation(view:TextInputLayout,value:String?){
    view.editText!!.addTextChangedListener(TextInputErrorClearListener(view));
}

fun View.setMotionVisibility(visibility: Int) {
    val motionLayout = parent as MotionLayout
    motionLayout.constraintSetIds.forEach {
        val constraintSet = motionLayout.getConstraintSet(it) ?: return@forEach
        constraintSet.setVisibility(this.id, visibility)
        constraintSet.applyTo(motionLayout)
    }
}

fun showSnackBar(view:View,message:String,type: SnackTypeEnum){
    var builder:SpannableStringBuilder  = SpannableStringBuilder();
    val sb: Snackbar = Snackbar.make(view,message, Snackbar.LENGTH_SHORT);
    val tv =sb.view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView;
    tv.textSize = 16f;
    tv.typeface = ResourcesCompat.getFont(view.context,R.font.source_sans_pro);
    tv.setTextColor(view.resources.getColor(R.color.whiteTextColor,null))

    when(type){
        SnackTypeEnum.SUCCESS->{
            sb.setBackgroundTint(view.resources.getColor(R.color.fistikYesil,null));
        }
        SnackTypeEnum.WARNING->{
            sb.setBackgroundTint(view.resources.getColor(R.color.acikTuruncu,null));
        }
        else->{
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

fun TextInputEditText.hideKeyboard(et:TextInputEditText){
    val imm = ContextCompat.getSystemService(et.context, InputMethodManager::class.java);
    imm?.hideSoftInputFromWindow(et.windowToken, 0);
}

fun TextInputEditText.clearContent(et:TextInputEditText){
    et.editableText.clear();
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

fun bitmapToFile(bitmap: Bitmap, fileNameToSave: String,requireContext: Context): File? {
    var file: File? = null
    return try {
        file = File(createOutputDirectory(requireContext),fileNameToSave);

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