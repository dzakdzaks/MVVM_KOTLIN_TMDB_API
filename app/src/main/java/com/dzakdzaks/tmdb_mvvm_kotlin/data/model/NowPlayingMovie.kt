package com.dzakdzaks.tmdb_mvvm_kotlin.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class NowPlayingMovie(

    @SerializedName("overview")
    @ColumnInfo(name = "overview")
    val overview: String? = "",

    @SerializedName("original_language")
    @ColumnInfo(name = "original_language")
    val originalLanguage: String? = "",

    @SerializedName("original_title")
    @ColumnInfo(name = "original_title")
    val originalTitle: String? = "",

    @SerializedName("video")
    @ColumnInfo(name = "video")
    val video: Boolean? = false,

    @SerializedName("title")
    @ColumnInfo(name = "title")
    val title: String? = "",

//    @Json(name = "genre_ids")
//    @ColumnInfo(name = "genre_ids")
//    val genreIds: List<Int?>? = null,

    @SerializedName("poster_path")
    @ColumnInfo(name = "poster_path")
    val posterPath: String? = "",

    @SerializedName("backdrop_path")
    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String? = "",

    @SerializedName("release_date")
    @ColumnInfo(name = "release_date")
    val releaseDate: String? = "",

    @SerializedName("popularity")
    @ColumnInfo(name = "popularity")
    val popularity: Double? = 0.0,

    @SerializedName("vote_average")
    @ColumnInfo(name = "vote_average")
    val voteAverage: Double? = 0.0,

    @SerializedName("id")
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int? = 0,

    @SerializedName("adult")
    @ColumnInfo(name = "adult")
    val adult: Boolean? = false,

    @SerializedName("vote_count")
    @ColumnInfo(name = "vote_count")
    val voteCount: Int? = 0
)