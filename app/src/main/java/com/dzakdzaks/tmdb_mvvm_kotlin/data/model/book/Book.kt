package com.dzakdzaks.tmdb_mvvm_kotlin.data.model.book

import com.google.gson.annotations.SerializedName

data class Book(

	@SerializedName("image")
	var image: String? = null,

	@SerializedName("updated_at")
	var updatedAt: String? = null,

	@SerializedName("author")
	var author: String? = null,

	@SerializedName("name")
	var name: String? = null,

	@SerializedName("created_at")
	var createdAt: String? = null,

	@SerializedName("id")
	var id: Int? = null
)