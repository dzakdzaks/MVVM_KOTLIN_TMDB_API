package com.dzakdzaks.tmdb_mvvm_kotlin.data.model

import com.google.gson.annotations.SerializedName

data class ResponseNowPlayingMovies(

    @SerializedName("dates")
    val dates: Dates? = null,

    @SerializedName("page")
    val page: Int? = 0,

    @SerializedName("total_pages")
    val totalPages: Int? = 0,

    @SerializedName("results")
    val results: List<NowPlayingMovie?>? = null,

    @SerializedName("total_results")
    val totalResults: Int? = 0
)