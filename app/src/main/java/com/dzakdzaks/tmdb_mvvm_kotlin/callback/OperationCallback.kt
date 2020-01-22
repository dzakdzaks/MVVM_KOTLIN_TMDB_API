package com.dzakdzaks.tmdb_mvvm_kotlin.callback

/**
 * ==================================//==================================
 * ==================================//==================================
 * Created by Dzakdzaks on Tuesday, 21 January 2020 at 17:48.
 * Project Name => TMDB_MVVM_KOTLIN
 * Package Name => com.dzakdzaks.tmdb_mvvm_kotlin.callback
 * ==================================//==================================
 * ==================================//==================================
 */
interface OperationCallback {
    fun onSuccess(obj: Any?)
    fun onError(obj: Any?)
}