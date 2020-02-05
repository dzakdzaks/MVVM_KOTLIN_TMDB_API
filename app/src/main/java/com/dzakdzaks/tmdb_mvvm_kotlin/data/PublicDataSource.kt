package com.dzakdzaks.tmdb_mvvm_kotlin.data

import androidx.lifecycle.LiveData
import com.dzakdzaks.tmdb_mvvm_kotlin.data.model.book.Book
import com.dzakdzaks.tmdb_mvvm_kotlin.data.model.book.ResponseBooks
import com.dzakdzaks.tmdb_mvvm_kotlin.data.model.movie.NowPlayingMovie
import com.dzakdzaks.tmdb_mvvm_kotlin.data.model.movie_detail.ResponseMovieDetail

/**
 * ==================================//==================================
 * ==================================//==================================
 * Created by Dzakdzaks on Tuesday, 21 January 2020 at 17:49.
 * Project Name => TMDB_MVVM_KOTLIN
 * Package Name => com.dzakdzaks.tmdb_mvvm_kotlin.data
 * ==================================//==================================
 * ==================================//==================================
 */

interface PublicDataSource {
    fun isLoading(): LiveData<Boolean>
    fun onErrorMessage(): LiveData<Any>
    fun listIsEmpty(): LiveData<Boolean>

    fun retrieveNowPlayingMovies(): LiveData<List<NowPlayingMovie>>
    fun retrieveMovieDetail(movieID: Int): LiveData<ResponseMovieDetail>
    fun retrieveAllBooks(): LiveData<List<Book>>
}