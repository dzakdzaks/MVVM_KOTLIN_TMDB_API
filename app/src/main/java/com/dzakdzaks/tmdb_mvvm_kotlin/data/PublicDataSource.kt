package com.dzakdzaks.tmdb_mvvm_kotlin.data

import androidx.lifecycle.LiveData
import com.dzakdzaks.tmdb_mvvm_kotlin.data.model.book.Book
import com.dzakdzaks.tmdb_mvvm_kotlin.data.model.book.RequestBookUpdate
import com.dzakdzaks.tmdb_mvvm_kotlin.data.model.book.ResponseBooks
import com.dzakdzaks.tmdb_mvvm_kotlin.data.model.book.ResponseUpdateDeleteBook
import com.dzakdzaks.tmdb_mvvm_kotlin.data.model.movie.NowPlayingMovie
import com.dzakdzaks.tmdb_mvvm_kotlin.data.model.movie_detail.ResponseMovieDetail
import okhttp3.MultipartBody
import okhttp3.RequestBody

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

    fun storeABook(name: RequestBody, author: RequestBody, image: MultipartBody.Part): LiveData<ResponseUpdateDeleteBook.ResponseUpdateDelete>
    fun retrieveAllBooks(): LiveData<List<Book>>
    fun retrieveBookByID(id: Int): LiveData<ResponseUpdateDeleteBook.ResponseUpdateDelete>
    fun bookUpdate(id: Int, requestBookUpdate: RequestBookUpdate): LiveData<ResponseUpdateDeleteBook.ResponseUpdateDelete>
    fun bookDelete(id: Int): LiveData<ResponseUpdateDeleteBook.ResponseUpdateDelete>
}