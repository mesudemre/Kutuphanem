package com.mesutemre.kutuphanem_ui.extensions

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.ContextWrapper
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import androidx.core.content.ContextCompat
import java.io.File
import java.util.concurrent.Executor

val Context.executor: Executor
    get() = ContextCompat.getMainExecutor(this)

fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("no activity")
}

fun Context.saveImageToFile(bitmap: Bitmap): File? {
    var uri: Uri? = null
    try {
        val fileName = System.nanoTime().toString() + ".jpg"
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/")
                put(MediaStore.MediaColumns.IS_PENDING, 1)
            } else {
                val directory =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                val file = File(directory, fileName)
                put(MediaStore.MediaColumns.DATA, file.absolutePath)
            }
        }

        uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        uri?.let {
            contentResolver.openOutputStream(it).use { output ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                values.apply {
                    clear()
                    put(MediaStore.Audio.Media.IS_PENDING, 0)
                }
                contentResolver.update(uri, values, null, null)
            }
        }
        return File(this.getRealPathFromURI(uri))
    } catch (e: java.lang.Exception) {
        if (uri != null) {
            contentResolver.delete(uri, null, null)
        }
        throw e
    }
}

fun Context.getRealPathFromURI_API19(uri: Uri?): String? {
    var filePath = ""
    val wholeID = DocumentsContract.getDocumentId(uri)
    val id = wholeID.split(":").toTypedArray()[1]
    val column = arrayOf(MediaStore.Images.Media.DATA)

    // where id is equal to
    val sel = MediaStore.Images.Media._ID + "=?"
    val cursor: Cursor? = this.contentResolver.query(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        column, sel, arrayOf(id), null
    )
    val columnIndex: Int = cursor!!.getColumnIndex(column[0])
    if (cursor.moveToFirst()) {
        filePath = cursor.getString(columnIndex)
    }
    cursor.close()
    return filePath
}

fun Context.getRealPathFromURI(contentUri: Uri?): String? {
    var cursor: Cursor? = null
    return try {
        val proj =
            arrayOf(MediaStore.Images.Media.DATA)
        cursor = this.contentResolver.query(contentUri!!, proj, null, null, null)
        cursor!!.moveToFirst()
        val column_index = cursor.getColumnIndex(proj[0])

        cursor.getString(column_index)
    } finally {
        cursor?.close()
    }
}