package com.dzakdzaks.tmdb_mvvm_kotlin.data.model.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class NowPlayingMovie(

    @SerializedName("overview")
    @ColumnInfo(name = "overview")
    var overview: String? = "",

    @SerializedName("original_language")
    @ColumnInfo(name = "original_language")
    var originalLanguage: String? = "",

    @SerializedName("original_title")
    @ColumnInfo(name = "original_title")
    var originalTitle: String? = "",

    @SerializedName("video")
    @ColumnInfo(name = "video")
    var video: Boolean? = false,

    @SerializedName("title")
    @ColumnInfo(name = "title")
    var title: String? = "",

//    @Json(name = "genre_ids")
//    @ColumnInfo(name = "genre_ids")
//    val genreIds: List<Int?>? = null,

    @SerializedName("poster_path")
    @ColumnInfo(name = "poster_path")
    var posterPath: String? = "",

    @SerializedName("backdrop_path")
    @ColumnInfo(name = "backdrop_path")
    var backdropPath: String? = "",

    @SerializedName("release_date")
    @ColumnInfo(name = "release_date")
    var releaseDate: String? = "",

    @SerializedName("popularity")
    @ColumnInfo(name = "popularity")
    var popularity: Double? = 0.0,

    @SerializedName("vote_average")
    @ColumnInfo(name = "vote_average")
    var voteAverage: Double? = 0.0,

    @SerializedName("id")
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int? = 0,

    @SerializedName("adult")
    @ColumnInfo(name = "adult")
    var adult: Boolean? = false,

    @SerializedName("vote_count")
    @ColumnInfo(name = "vote_count")
    var voteCount: Int? = 0
)