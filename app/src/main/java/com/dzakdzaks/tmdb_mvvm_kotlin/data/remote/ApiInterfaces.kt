package com.dzakdzaks.tmdb_mvvm_kotlin.data.remote

import com.dzakdzaks.tmdb_mvvm_kotlin.data.model.ResponseNowPlayingMovies
import com.dzakdzaks.tmdb_mvvm_kotlin.data.model.book.RequestBookUpdate
import com.dzakdzaks.tmdb_mvvm_kotlin.data.model.book.ResponseBooks
import com.dzakdzaks.tmdb_mvvm_kotlin.data.model.book.ResponseUpdateDeleteBook
import com.dzakdzaks.tmdb_mvvm_kotlin.data.model.movie_detail.ResponseMovieDetail
import retrofit2.Call
import retrofit2.http.*

/**
 * ==================================//==================================
 * ==================================//==================================
 * Created by Dzakdzaks on Tuesday, 21 January 2020 at 17:36.
 * Project Name => TMDB_MVVM_KOTLIN
 * Package Name => com.dzakdzaks.tmdb_mvvm_kotlin.data
 * ==================================//==================================
 * ==================================//==================================
 */
interface ApiInterfaces {

    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @Query("api_key") apiKey: String
    ): Call<ResponseNowPlayingMovies>


    @GET("movie/{movie_id}")
    fun getMovieDetail(
        @Path("movie_id") movieID: Int,
        @Query("api_key") apiKey: String
    ): Call<ResponseMovieDetail>

    @Headers("Accept: application/json")
    @GET("books")
    fun getAllBooks(): Call<ResponseBooks>

    @Headers("Accept: application/json")
    @PUT("books/{id}")
    fun updateBook(
        @Path("id") id: Int,
        @Body body: RequestBookUpdate
    ): Call<ResponseUpdateDeleteBook.ResponseUpdateDelete>


}