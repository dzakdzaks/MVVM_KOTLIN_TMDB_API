package com.dzakdzaks.tmdb_mvvm_kotlin.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dzakdzaks.tmdb_mvvm_kotlin.data.model.NowPlayingMovie

/**
 * ==================================//==================================
 * ==================================//==================================
 * Created by Dzakdzaks on Tuesday, 21 January 2020 at 17:59.
 * Project Name => TMDB_MVVM_KOTLIN
 * Package Name => com.dzakdzaks.tmdb_mvvm_kotlin.data.local
 * ==================================//==================================
 * ==================================//==================================
 */
@Dao
interface NowPlayingMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveNowPlayingMovies(data: NowPlayingMovie)

    @Query(value = "SELECT * FROM NowPlayingMovie")
    fun getNowPlayingMovies(): List<NowPlayingMovie>

}