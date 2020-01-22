package com.dzakdzaks.tmdb_mvvm_kotlin.data.local

import androidx.room.Room
import com.dzakdzaks.tmdb_mvvm_kotlin.MainApplication
import com.dzakdzaks.tmdb_mvvm_kotlin.data.model.NowPlayingMovie

/**
 * ==================================//==================================
 * ==================================//==================================
 * Created by Dzakdzaks on Tuesday, 21 January 2020 at 18:01.
 * Project Name => TMDB_MVVM_KOTLIN
 * Package Name => com.dzakdzaks.tmdb_mvvm_kotlin.data.local
 * ==================================//==================================
 * ==================================//==================================
 */

class LocalRepository {

    private val db =
        Room.databaseBuilder(MainApplication.appContext(), LocalDB::class.java, "LocalDB").build()

    fun saveNowPlayingMovies(museumEntity: NowPlayingMovie) {
        db.nowPlayingMovies().saveNowPlayingMovies(museumEntity)
    }

    fun getNowPlayingMovies(): List<NowPlayingMovie> {
        return db.nowPlayingMovies().getNowPlayingMovies()
    }

}