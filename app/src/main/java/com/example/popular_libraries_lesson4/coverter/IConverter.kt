package com.example.popular_libraries_lesson4.coverter

import com.example.popular_libraries_lesson4.mvp.model.IImage
import io.reactivex.rxjava3.core.Completable
import java.io.File

interface IConverter {
    fun convert(image: IImage, destFileName: String): Completable
}