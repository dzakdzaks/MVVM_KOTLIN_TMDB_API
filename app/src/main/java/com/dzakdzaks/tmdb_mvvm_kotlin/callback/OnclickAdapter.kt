package com.dzakdzaks.tmdb_mvvm_kotlin.callback

import android.view.View
import com.dzakdzaks.tmdb_mvvm_kotlin.data.model.book.Book

/**
 * ==================================//==================================
 * ==================================//==================================
 * Created by Dzakdzaks on Tuesday, 21 January 2020 at 17:48.
 * Project Name => TMDB_MVVM_KOTLIN
 * Package Name => com.dzakdzaks.tmdb_mvvm_kotlin.callback
 * ==================================//==================================
 * ==================================//==================================
 */
interface OnclickAdapter {
    fun onItemClick(any: Any)
    fun onItemLongClick(any: Any, view: View)
}