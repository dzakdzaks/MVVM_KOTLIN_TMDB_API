package com.dzakdzaks.tmdb_mvvm_kotlin.data.model.movie_detail

import com.google.gson.annotations.SerializedName

data class ProductionCompaniesItem(

	@SerializedName("logo_path")
	val logoPath: String? = "",

	@SerializedName("name")
	val name: String? = "",

	@SerializedName("id")
	val id: Int? = 0,

	@SerializedName("origin_country")
	val originCountry: String? = ""
)