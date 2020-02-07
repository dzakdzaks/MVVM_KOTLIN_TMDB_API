package com.dzakdzaks.tmdb_mvvm_kotlin.data.remote

import com.dzakdzaks.tmdb_mvvm_kotlin.data.model.ResponseNowPlayingMovies
import com.dzakdzaks.tmdb_mvvm_kotlin.data.model.book.RequestBookUpdate
import com.dzakdzaks.tmdb_mvvm_kotlin.data.model.book.ResponseBooks
import com.dzakdzaks.tmdb_mvvm_kotlin.data.model.book.ResponseUpdateDeleteBook
import com.dzakdzaks.tmdb_mvvm_kotlin.data.model.movie_detail.ResponseMovieDetail
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
    @Multipart
    @POST("books")
    fun storeBook(
        @Part("name") name: RequestBody,
        @Part("author") author: RequestBody,
        @Part image: MultipartBody.Part
    ): Call<ResponseUpdateDeleteBook.ResponseUpdateDelete>

    @Headers("Accept: application/json")
    @GET("books")
    fun getAllBooks(): Call<ResponseBooks>

    @Headers("Accept: application/json")
    @GET("books/{id}")
    fun getBookByID(
        @Path("id") id: Int
    ): Call<ResponseUpdateDeleteBook.ResponseUpdateDelete>

    @Headers("Accept: application/json")
    @PUT("books/{id}")
    fun updateBook(
        @Path("id") id: Int,
        @Body body: RequestBookUpdate
    ): Call<ResponseUpdateDeleteBook.ResponseUpdateDelete>

    @Headers("Accept: application/json")
    @DELETE("books/{id}")
    fun deleteBook(
        @Path("id") id: Int
    ): Call<ResponseUpdateDeleteBook.ResponseUpdateDelete>

}