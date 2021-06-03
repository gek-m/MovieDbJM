package com.example.moviedbjm.domain.responses

import com.google.gson.annotations.SerializedName

data class TmdbMovie(
    @SerializedName("id")
    val id: Int,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("overview")
    val overview: String
)

