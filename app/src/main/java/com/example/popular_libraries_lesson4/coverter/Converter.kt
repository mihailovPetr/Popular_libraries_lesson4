package com.example.popular_libraries_lesson4.coverter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import com.example.popular_libraries_lesson4.mvp.model.IImage
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream


class Converter(private val context: Context) : IConverter {

    override fun convert(image: IImage, destFileName: String): Completable = Completable.fromAction {

        try {
            Thread.sleep(0)
        } catch (e: InterruptedException) {
        }

        val bytes = context.contentResolver.openInputStream(Uri.parse(image.getPath()))?.buffered()
            ?.use { it.readBytes() }

        bytes?.let {
            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            val destFile = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), destFileName)
            FileOutputStream(destFile).use {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
            }
        }
    }.subscribeOn(Schedulers.io())

}