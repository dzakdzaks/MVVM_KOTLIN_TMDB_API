package com.dzakdzaks.tmdb_mvvm_kotlin.data.model.movie_detail

import com.google.gson.annotations.SerializedName

data class SpokenLanguagesItem(

	@SerializedName("name")
	val name: String? = "",

	@SerializedName("iso_639_1")
	val iso6391: String? = ""
)