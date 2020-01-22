package com.dzakdzaks.tmdb_mvvm_kotlin.data.model.movie_detail

import com.google.gson.annotations.SerializedName

data class GenresItem(

	@SerializedName("name")
	val name: String? = "",

	@SerializedName("id")
	val id: Int? = 0
)