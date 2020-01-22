package com.dzakdzaks.tmdb_mvvm_kotlin.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dzakdzaks.tmdb_mvvm_kotlin.data.model.NowPlayingMovie

/**
 * ==================================//==================================
 * ==================================//==================================
 * Created by Dzakdzaks on Tuesday, 21 January 2020 at 17:56.
 * Project Name => TMDB_MVVM_KOTLIN
 * Package Name => com.dzakdzaks.tmdb_mvvm_kotlin.data.local
 * ==================================//==================================
 * ==================================//==================================
 */
@Database(entities = [(NowPlayingMovie::class)], version = 1)
abstract class LocalDB: RoomDatabase() {

    abstract fun nowPlayingMovies(): NowPlayingMovieDao
}