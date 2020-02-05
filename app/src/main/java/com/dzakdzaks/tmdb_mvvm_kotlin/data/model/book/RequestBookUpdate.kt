package com.dzakdzaks.tmdb_mvvm_kotlin.data.model.book

import com.google.gson.annotations.SerializedName

/**
 * ==================================//==================================
 * ==================================//==================================
 * Created by Dzakdzaks on Wednesday, 05 February 2020 at 14:01.
 * Project Name => TMDB_MVVM_KOTLIN
 * Package Name => com.dzakdzaks.tmdb_mvvm_kotlin.data.model.book
 * ==================================//==================================
 * ==================================//==================================
 */

data class RequestBookUpdate(

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("author")
    var author: String? = null
)