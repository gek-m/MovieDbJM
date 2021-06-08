package com.example.moviedbjm.domain.responses

import com.example.moviedbjm.BuildConfig
import com.example.moviedbjm.domain.Movie

fun TmdbCategory.parseToMovieList(): List<Movie> {
    val movieList: ArrayList<Movie> = arrayListOf()

    this.results.let {
        it.forEach { tmdbMovie ->
            movieList.add(
                Movie(
                    id = tmdbMovie.id,
                    title = tmdbMovie.originalTitle,
                    releaseDate = tmdbMovie.releaseDate,
                    voteAverage = tmdbMovie.voteAverage,
                    posterPath = "${BuildConfig.IMAGE_BASE_URL}t/p/w342${tmdbMovie.posterPath}",
                    overview = tmdbMovie.overview
                )
            )
        }
    }

    return movieList
}