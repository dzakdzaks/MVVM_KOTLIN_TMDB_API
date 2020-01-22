package com.dzakdzaks.tmdb_mvvm_kotlin.data.model.movie_detail

import com.google.gson.annotations.SerializedName

data class ProductionCountriesItem(

	@SerializedName("iso_3166_1")
	val iso31661: String? = "",

	@SerializedName("name")
	val name: String? = ""
)