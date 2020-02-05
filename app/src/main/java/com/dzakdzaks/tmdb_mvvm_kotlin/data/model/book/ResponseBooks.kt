package com.dzakdzaks.tmdb_mvvm_kotlin.data.model.book

import com.google.gson.annotations.SerializedName

data class ResponseBooks(

    @SerializedName("data")
	var data: List<Book?>? = null,

    @SerializedName("success")
    var success: Boolean? = null,

    @SerializedName("message")
    var message: String? = null
)