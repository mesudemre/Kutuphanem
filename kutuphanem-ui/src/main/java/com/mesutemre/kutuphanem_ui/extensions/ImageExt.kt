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
