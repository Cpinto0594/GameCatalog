package com.cpinto.gamecatalog.application.extension_funtions

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import com.bumptech.glide.Glide
import java.io.File
import java.io.FileOutputStream

object ShareContentProvider {

    fun imageToBitMap(context: Context, imageUrl: String): Bitmap =
        Glide.with(context)
            .asBitmap()
            .load(imageUrl)
            .submit()
            .get()

    fun saveImageToLocal(
        context: Context,
        bitmap: Bitmap,
        imageName: String,
        imageFormat: String
    ): File {
        val outputStream: FileOutputStream
        try {
            val storagePath = ContextWrapper(context).getDir("imageDir", Context.MODE_PRIVATE)
            val fileName = File(storagePath, imageName + imageFormat)
            outputStream = FileOutputStream(fileName)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.close()
            return fileName
        } catch (error: Exception) {
            error.printStackTrace()
        }
        return File("")
    }

}