package com.dzakdzaks.tmdb_mvvm_kotlin

import android.app.Application
import android.content.Context

/**
 * ==================================//==================================
 * ==================================//==================================
 * Created by Dzakdzaks on Tuesday, 21 January 2020 at 17:31.
 * Project Name => TMDB_MVVM_KOTLIN
 * Package Name => com.dzakdzaks.tmdb_mvvm_kotlin
 * ==================================//==================================
 * ==================================//==================================
 */

class MainApplication: Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: MainApplication? = null

        fun appContext(): Context {
            return instance!!.applicationContext
        }
    }

}
