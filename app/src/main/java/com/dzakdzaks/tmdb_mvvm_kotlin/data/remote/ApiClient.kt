package com.dzakdzaks.tmdb_mvvm_kotlin.data.remote

import com.dzakdzaks.tmdb_mvvm_kotlin.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

/**
 * ==================================//==================================
 * ==================================//==================================
 * Created by Dzakdzaks on Tuesday, 21 January 2020 at 17:34.
 * Project Name => TMDB_MVVM_KOTLIN
 * Package Name => com.dzakdzaks.tmdb_mvvm_kotlin.data
 * ==================================//==================================
 * ==================================//==================================
 */

object ApiClient {
    private var servicesApiInterface: ApiInterfaces? = null

    fun build(isMovies: Boolean): ApiInterfaces? {

        val builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(if (isMovies) BuildConfig.BASE_URL else BuildConfig.BASE_URL_BOOKS)
            .addConverterFactory(GsonConverterFactory.create())

        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
            .addNetworkInterceptor(AddHeaderInterceptor())
            .addInterceptor(interceptor())

        val retrofit: Retrofit = builder.client(httpClient.build()).build()
        servicesApiInterface = retrofit.create(
            ApiInterfaces::class.java
        )

        return servicesApiInterface as ApiInterfaces
    }

    private fun interceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return httpLoggingInterceptor
    }

    class AddHeaderInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {

            val builder = chain.request().newBuilder()
            builder.addHeader("Authorization", BuildConfig.BEARER)

            return chain.proceed(builder.build())
        }
    }

}