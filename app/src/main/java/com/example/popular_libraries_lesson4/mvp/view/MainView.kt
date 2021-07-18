package com.example.popular_libraries_lesson4.mvp.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface MainView : MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun selectImage()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun onConvertComplete()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun onConvertError()

    fun showConvertDialog()

    fun hideConvertDialog()
}