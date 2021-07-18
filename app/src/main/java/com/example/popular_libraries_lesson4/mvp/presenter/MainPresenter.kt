package com.example.popular_libraries_lesson4.mvp.presenter

import android.util.Log
import android.util.Log.ASSERT
import com.example.popular_libraries_lesson4.coverter.IConverter
import com.example.popular_libraries_lesson4.mvp.model.IImage
import com.example.popular_libraries_lesson4.mvp.view.MainView
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import moxy.MvpPresenter
import java.io.File

class MainPresenter(private val converter: IConverter, private val mainThreadScheduler: Scheduler) :
    MvpPresenter<MainView>() {

    private var disposable: Disposable? = null

    fun buttonClick() {
        viewState.selectImage()
    }

    fun imageResult(image: IImage) {
        viewState.showConvertDialog()
        disposable = converter.convert(image, "destFile.png")
            .observeOn(mainThreadScheduler).doOnSubscribe {}
            .subscribe({
                viewState.hideConvertDialog()
                viewState.onConvertComplete()
            }, {
                viewState.hideConvertDialog()
                viewState.onConvertError()
            })
    }

    fun cancelClick(){
        disposable?.dispose()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }
}