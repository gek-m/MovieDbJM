package com.example.moviedbjm.network.responses

import com.example.moviedbjm.BuildConfig
import com.example.moviedbjm.domain.Movie

fun TmdbCategory.parseToMovieList(isAdult: Boolean): List<Movie> {
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
                    overview = tmdbMovie.overview,
                    adult = tmdbMovie.adult
                )
            )
        }
    }

    if (!isAdult) {
        return movieList.filter { !it.adult }
    }

    return movieList
}