package com.mesutemre.kutuphanem.util

import android.app.Activity
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.text.SpannableStringBuilder
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.RemoteViews
import android.widget.TextView
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.core.app.NotificationCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.work.*
import com.google.android.material.snackbar.Snackbar
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.kitap.liste.model.KitapModel
import com.mesutemre.kutuphanem.model.ERROR
import com.mesutemre.kutuphanem.model.SUCCESS
import com.mesutemre.kutuphanem.model.SnackType
import com.mesutemre.kutuphanem.model.WARNING
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

const val APP_TOKEN_KEY: String = "APP_TOKEN"
const val KULLANICI_ADI_KEY: String = "KULLANICI_ADI"
const val KUTUPHANEM_DB_NAME = "kutuphanem"
const val PARAM_YAYINEVI_DB_KEY: String = "PARAM_YAYINEVI"
const val PARAM_KITAPTUR_DB_KEY: String = "PARAM_KITAPTUR"
const val KULLANICI_DB_MEVCUT: String = "KULLANICI_MEVCUT"
const val DASHBOARD_KATEGORI_ISTATISTIK = "DASHBOARD_KATEGORI_ISTATISTIK"
const val DASHBOARD_YIL_ISTATISTIK = "DASHBOARD_YIL_ISTATISTIK"
const val CAMERA_REQUEST_CODE: Int = 1991
const val READ_EXTERNAL_STORAGE_REQUEST_CODE: Int = 1992
const val WRITE_EXTERNAL_STORAGE_REQUEST_CODE: Int = 2019
const val SHARED_PREF_FILE: String = "KUTUPHANEM_SP"

val DEVICE_NAME: String by lazy {
    val manufacturer = Build.MANUFACTURER
    val model = Build.MODEL
    if (model.toLowerCase().startsWith(manufacturer.toLowerCase())) {
        capitalize(model)
    } else {
        capitalize(manufacturer) + " " + model
    }
}

private fun capitalize(s: String?): String {
    if (s == null || s.isEmpty()) {
        return ""
    }
    val first = s[0]
    return if (Character.isUpperCase(first)) {
        s
    } else {
        Character.toUpperCase(first) + s.substring(1)
    }
}

fun showSnackBar(view: View, message: String, @SnackType type: Int) {
    var builder: SpannableStringBuilder = SpannableStringBuilder();
    val sb: Snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
    val tv = sb.view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView;
    tv.textSize = 16f;
    tv.typeface = ResourcesCompat.getFont(view.context, R.font.source_sans_pro_regular);
    tv.setTextColor(view.resources.getColor(R.color.whiteTextColor, null))

    when (type) {
        SUCCESS -> {
            sb.setBackgroundTint(view.resources.getColor(R.color.fistikYesil, null));
        }
        WARNING -> {
            sb.setBackgroundTint(view.resources.getColor(R.color.acikTuruncu, null));
        }
        ERROR -> {
            sb.setBackgroundTint(view.resources.getColor(R.color.kirmizi, null));
        }

    }
    builder.append(message);

    sb.setText(builder);
    sb.show();
}

fun formatDate(date: Date, pattern: String): String {
    val sdf: SimpleDateFormat = SimpleDateFormat(pattern);
    return sdf.format(date);
}

fun getImageUriFromBitmap(c: Context, inImage: Bitmap): Uri {
    var path: String = "";
    try {
        val bytes: ByteArrayOutputStream = ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        path = MediaStore.Images.Media.insertImage(
            c.getContentResolver(),
            inImage,
            "KUTUPHANEM_IMG_" + System.currentTimeMillis(),
            null
        );

    } catch (e: Exception) {
        Log.e("Exception", e.localizedMessage);
        e.printStackTrace();
    }
    return Uri.parse(path);
}

fun getPath(context: Context, uri: Uri): String? {
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
            val contentUri = ContentUris.withAppendedId(
                Uri.parse("content://downloads/public_downloads"),
                java.lang.Long.valueOf(id)
            )
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

fun getStreamFile(context: Context, uri: Uri): String? {
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

private fun isExternalStorageDocument(uri: Uri): Boolean {
    return "com.android.externalstorage.documents".equals(uri.getAuthority());
}

private fun isDownloadsDocument(uri: Uri): Boolean {
    return "com.android.providers.downloads.documents".equals(uri.getAuthority());
}

private fun isMediaDocument(uri: Uri): Boolean {
    return "com.android.providers.media.documents".equals(uri.getAuthority());
}

@BindingAdapter(value = ["android:rippleEffect"])
fun rippleEffect(view: View, value: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val outValue = TypedValue();
        view.context.theme.resolveAttribute(
            android.R.attr.selectableItemBackground,
            outValue,
            true
        );
        view.foreground = view.context.getDrawable(outValue.resourceId);
    }
}

fun checkDeviceHasCamera(ctx: Context): Boolean {
    if (ctx.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
        return true;
    }
    return false;
}

fun checkDeviceHasFronCamera(ctx: Context): Boolean {
    if (ctx.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)) {
        return true;
    }
    return false;
}

fun saveFile(
    kitap: KitapModel,
    requireContext: Context,
    isArchive: Boolean,
    arr: ByteArray
): File? {
    val kitapResim = BitmapFactory.decodeByteArray(arr, 0, arr.size);
    val bytes: ByteArrayOutputStream = ByteArrayOutputStream();
    var photoFile: File? = null;
    if (kitapResim != null) {
        kitapResim?.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        var resimAd: String = kitap.kitapAd + "_" + kitap.kitapId
        if (isArchive) {
            resimAd = kitap.kitapId.toString();
        }

        photoFile = convertBitmapToFile(kitapResim!!, resimAd + ".png", requireContext);
    }
    return photoFile;
}

fun convertBitmapToFile(bitmap: Bitmap, fileNameToSave: String, requireContext: Context): File {
    var photoFile: File? = null;
    return try {
        val imgPath: File = requireContext.createOutputDirectory("arsiv")
        photoFile = File(imgPath.absolutePath, fileNameToSave)

        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos) // YOU can also save it in JPEG
        val bitmapdata = bos.toByteArray()

        val fos = FileOutputStream(photoFile)
        fos.write(bitmapdata)
        fos.flush()
        fos.close()
        photoFile
    } catch (e: Exception) {
        e.printStackTrace()
        photoFile!! // it will return null
    }
}

inline fun <reified T : Activity>
        Context.startActivity() {
    val intent = Intent(this, T::class.java);
    startActivity(intent);
}

fun Context.arsivResimSil(kitapId: String) {
    val directory = File(this.filesDir, "arsiv");
    val f = File(directory.absolutePath + "/${kitapId}.png");
    if (f.exists()) {
        f.delete();
    }
}

inline fun <reified W : ListenableWorker>
        Context.enqueeOneTimeWorkManager(
    constraints: Constraints = Constraints.Builder()
        .setRequiresBatteryNotLow(true)
        .build()
) {
    val otwr = OneTimeWorkRequestBuilder<W>()
        .setConstraints(constraints)
        .build()
    WorkManager.getInstance(this).enqueue(otwr);
}

inline fun <reified W : ListenableWorker>
        Context.enqueePeriodicTimeWorkManager(
    workTag: String,
    constraints: Constraints = Constraints.Builder()
        .setRequiresBatteryNotLow(true)
        .build()
) {
    val pwr = PeriodicWorkRequestBuilder<W>(1, TimeUnit.DAYS)
        .setConstraints(constraints)
        .build();
    WorkManager.getInstance(this)
        .enqueueUniquePeriodicWork(workTag, ExistingPeriodicWorkPolicy.KEEP, pwr);

}

fun Context.showKutuphanemBasicNotification(
    baslik: String,
    aciklama: String
) {
    val builder: NotificationCompat.Builder;
    val bildirimYoneticisi =
        this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager;
    val notificationLayout =
        RemoteViews(this.packageName, R.layout.kutuphanem_basic_notification_layout);

    notificationLayout.setTextViewText(R.id.notificationTitleTextView, baslik);
    notificationLayout.setTextViewText(R.id.notificationDetailTextView, aciklama);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val kanalId = this.getString(R.string.kutuphanem_notification_channel_id)
        val kanalAd = this.getString(R.string.kutuphanem_notification_channel_name)
        val kanalTanitim = this.getString(R.string.kutuphanem_notification_channel_advertise)

        var kanal: NotificationChannel? = bildirimYoneticisi.getNotificationChannel(kanalId)

        if (kanal == null) {
            kanal = NotificationChannel(kanalId, kanalAd, NotificationManager.IMPORTANCE_DEFAULT);
            kanal.description = kanalTanitim;
            kanal.vibrationPattern = longArrayOf(0, 200, 60, 200)

            bildirimYoneticisi.createNotificationChannel(kanal);
        }

        builder = NotificationCompat.Builder(this, kanalId);
        builder
            .setSmallIcon(R.drawable.ic_stat_onesignal_default)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(notificationLayout)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setAutoCancel(true);
    } else {
        builder = NotificationCompat.Builder(this);
        builder
            .setSmallIcon(R.drawable.ic_stat_onesignal_default)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(notificationLayout)
            .setAutoCancel(true)
            .priority = Notification.PRIORITY_DEFAULT;
    }
    bildirimYoneticisi.notify(2, builder.build());
}

fun downloadKitap(requireContext: Context, arr: ByteArray): File? {
    var kitapFile: File? = null;

    return try {
        val imgPath: File = requireContext.createOutputDirectory("kitapdownloads")
        kitapFile = File(imgPath.absolutePath, "kotlin.pdf")

        val fos = FileOutputStream(kitapFile)
        fos.write(arr)
        fos.flush()
        fos.close()
        kitapFile
    } catch (e: Exception) {
        e.printStackTrace()
        kitapFile!! // it will return null
    }
}

fun Fragment.closeApplication() {
    this.activity?.finish()
}

fun Context.createOutputDirectory(folderPath: String): File {
    val mediaDir = this.filesDir.let {
        File(it, folderPath).apply { mkdirs() }
    }
    return if (mediaDir != null && mediaDir.exists())
        mediaDir else this.filesDir
}

fun <R, C> BaseResourceEvent<R>.convertRersourceEventType(
    convert: () -> C,
): BaseResourceEvent<C> {
    return if (this is BaseResourceEvent.Success) {
        BaseResourceEvent.Success(data = convert.invoke())
    }else if (this is BaseResourceEvent.Error) {
        BaseResourceEvent.Error(message = this.message)
    } else {
        BaseResourceEvent.Loading()
    }
}

@Composable
fun getColorListForPieChart(): List<Color> {
    return listOf(
        MaterialTheme.colorPalette.acik_kirmizi,
        MaterialTheme.colorPalette.fistikYesil,
        MaterialTheme.colorPalette.lacivert,
        MaterialTheme.colorPalette.kahverengi,
        MaterialTheme.colorPalette.acikMor,
        MaterialTheme.colorPalette.aero,
        MaterialTheme.colorPalette.etonBlue,
        MaterialTheme.colorPalette.turuncu,
        MaterialTheme.colorPalette.salmonPink,
        MaterialTheme.colorPalette.sari,
        MaterialTheme.colorPalette.spaceCadet,
        MaterialTheme.colorPalette.morningBlue
    )
}

@Composable
fun RandomComposeColor(): Color {
    return arrayOf(
        MaterialTheme.colorPalette.acik_kirmizi,
        MaterialTheme.colorPalette.acikTuruncu,
        MaterialTheme.colorPalette.acikSari,
        MaterialTheme.colorPalette.fistikYesil,
        MaterialTheme.colorPalette.lacivert,
        MaterialTheme.colorPalette.shrim_gray,
        MaterialTheme.colorPalette.kahverengi,
        MaterialTheme.colorPalette.googleDarkGray,
        MaterialTheme.colorPalette.acikMor,
        MaterialTheme.colorPalette.aero,
        MaterialTheme.colorPalette.kirikBeyaz,
        MaterialTheme.colorPalette.etonBlue,
        MaterialTheme.colorPalette.turuncu,
        MaterialTheme.colorPalette.salmonPink,
        MaterialTheme.colorPalette.primaryTextColor,
        MaterialTheme.colorPalette.spaceCadet,
        MaterialTheme.colorPalette.morningBlue
    ).random()
}