package com.example.popular_libraries_lesson4.mvp.model

import android.net.Uri

class Image(private val uri: Uri) : IImage {

    override fun getPath(): String {
        return uri.toString()
    }
}