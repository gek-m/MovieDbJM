package com.example.moviedbjm.domain

import java.util.concurrent.Executor

interface MovieRepository {
    fun getMovieList(
        executor: Executor,
        callback: (result: RepositoryResult<List<Movie>>) -> Unit
    )

    fun getMovieDetails(
        executor: Executor,
        callback: (result: RepositoryResult<Movie>) -> Unit
    )
}