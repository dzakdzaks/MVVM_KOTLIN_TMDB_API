package com.dzakdzaks.tmdb_mvvm_kotlin.data.model.book

import com.google.gson.annotations.SerializedName

class ResponseUpdateDeleteBook {
	data class ResponseUpdateDelete(

		@field:SerializedName("data")
		var data: Data? = null,

		@field:SerializedName("success")
		var success: Boolean? = null,

		@field:SerializedName("message")
		var message: String? = null
	)

	data class Data(

		@field:SerializedName("image")
		var image: String? = null,

		@field:SerializedName("updated_at")
		var updatedAt: String? = null,

		@field:SerializedName("author")
		var author: String? = null,

		@field:SerializedName("name")
		var name: String? = null,

		@field:SerializedName("created_at")
		var createdAt: String? = null,

		@field:SerializedName("id")
		var id: Int? = null
	)

}