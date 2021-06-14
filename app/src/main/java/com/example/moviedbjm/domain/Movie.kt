package com.example.moviedbjm.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int = MovieDefaultValue.MOVIE_ID,
    val title: String = MovieDefaultValue.MOVIE_TITLE,
    val releaseDate: String = MovieDefaultValue.MOVIE_RELEASE_DATE,
    val voteAverage: Double = MovieDefaultValue.MOVIE_VOTE_AVERAGE,
    val posterPath: String = MovieDefaultValue.MOVIE_POSTER_PATH,
    val overview: String = MovieDefaultValue.MOVIE_OVERVIEW,
    val adult: Boolean = false
): Parcelable
