package com.mesutemre.kutuphanem_ui.extensions

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import java.io.File
import java.io.IOException

fun Bitmap.rotateBitmap(fileName: String): Bitmap {
    try {
        val exif = ExifInterface(fileName)
        val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
        if (orientation == 1) {
            return this
        }
        val matrix = Matrix()
        when (orientation) {
            2 -> matrix.setScale(-1f, 1f)
            3 -> matrix.setRotate(180f)
            4 -> {
                matrix.setRotate(180f)
                matrix.postScale(-1f, 1f)
            }
            5 -> {
                matrix.setRotate(90f)
                matrix.postScale(-1f, 1f)
            }
            6 -> matrix.setRotate(90f)
            7 -> {
                matrix.setRotate(-90f)
                matrix.postScale(-1f, 1f)
            }
            8 -> matrix.setRotate(-90f)
            else -> return this
        }
        return try {
            val oriented =
                Bitmap.createBitmap(this, 0, 0, this.width, this.height, matrix, true)
            this.recycle()
            oriented
        } catch (e: OutOfMemoryError) {
            e.printStackTrace()
            this
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return this
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
