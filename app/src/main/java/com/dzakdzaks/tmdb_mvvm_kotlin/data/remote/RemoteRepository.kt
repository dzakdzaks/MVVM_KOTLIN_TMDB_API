package com.dzakdzaks.tmdb_mvvm_kotlin.data.remote

import android.util.Log
import com.dzakdzaks.tmdb_mvvm_kotlin.BuildConfig
import com.dzakdzaks.tmdb_mvvm_kotlin.callback.OperationCallback
import com.dzakdzaks.tmdb_mvvm_kotlin.data.model.ResponseNowPlayingMovies
import com.dzakdzaks.tmdb_mvvm_kotlin.data.model.book.ResponseBooks
import com.dzakdzaks.tmdb_mvvm_kotlin.data.model.movie_detail.ResponseMovieDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * ==================================//==================================
 * ==================================//==================================
 * Created by Dzakdzaks on Tuesday, 21 January 2020 at 18:03.
 * Project Name => TMDB_MVVM_KOTLIN
 * Package Name => com.dzakdzaks.tmdb_mvvm_kotlin.data.remote
 * ==================================//==================================
 * ==================================//==================================
 */

class RemoteRepository {

    private var callNowPlayingMovies: Call<ResponseNowPlayingMovies>? = null
    private var callMovieDetail: Call<ResponseMovieDetail>? = null
    private var callBooks: Call<ResponseBooks>? = null

    fun retrieveNowPlayingMovies(callback: OperationCallback) {
        callNowPlayingMovies = ApiClient.build(true)?.getNowPlayingMovies(BuildConfig.API_KEY)
        callNowPlayingMovies?.enqueue(object : Callback<ResponseNowPlayingMovies> {
            override fun onFailure(call: Call<ResponseNowPlayingMovies>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(
                call: Call<ResponseNowPlayingMovies>,
                response: Response<ResponseNowPlayingMovies>
            ) {
                response.body()?.let {
                    if (response.isSuccessful) {
                        Log.d("nowPlayingMoview", "data : ${it.results}")
                        callback.onSuccess(it.results)
                    } else {
                        callback.onError("Not Successfully")
                    }
                }
            }

        })
    }

    fun retrieveMovieDetail(movieID: Int, callback: OperationCallback) {
        callMovieDetail = ApiClient.build(true)?.getMovieDetail(movieID, BuildConfig.API_KEY)
        callMovieDetail?.enqueue(object : Callback<ResponseMovieDetail> {
            override fun onFailure(call: Call<ResponseMovieDetail>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(
                call: Call<ResponseMovieDetail>,
                response: Response<ResponseMovieDetail>
            ) {
                response.body().let {
                    if (response.isSuccessful) {
                        callback.onSuccess(it)
                    } else {
                        callback.onError("Not Successfully")
                    }
                }
            }

        })
    }

    fun retrieveAllBooks(callback: OperationCallback) {
        callBooks = ApiClient.build(false)?.getAllBooks()
        callBooks?.enqueue(object : Callback<ResponseBooks> {
            override fun onFailure(call: Call<ResponseBooks>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(call: Call<ResponseBooks>, response: Response<ResponseBooks>) {
                response.body().let {
                    if (response.isSuccessful) {
                        Log.d("kambing", "data : ${it?.data}")
                        callback.onSuccess(it?.data)
                    } else {
                        callback.onError("Not Successfully")
                    }
                }
            }

        })
    }
}