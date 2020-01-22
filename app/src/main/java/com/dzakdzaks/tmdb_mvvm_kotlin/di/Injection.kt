package com.dzakdzaks.tmdb_mvvm_kotlin.di

import com.dzakdzaks.tmdb_mvvm_kotlin.data.PublicRepository
import com.dzakdzaks.tmdb_mvvm_kotlin.data.local.LocalRepository
import com.dzakdzaks.tmdb_mvvm_kotlin.data.remote.RemoteRepository

/**
 * ==================================//==================================
 * ==================================//==================================
 * Created by Dzakdzaks on Tuesday, 21 January 2020 at 17:55.
 * Project Name => TMDB_MVVM_KOTLIN
 * Package Name => com.dzakdzaks.tmdb_mvvm_kotlin.di
 * ==================================//==================================
 * ==================================//==================================
 */
object Injection {
    fun providerRepository(): PublicRepository {
        return PublicRepository(LocalRepository(), RemoteRepository())
    }
}