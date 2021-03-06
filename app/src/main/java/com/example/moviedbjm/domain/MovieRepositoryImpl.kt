package com.example.moviedbjm.domain

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import kotlin.random.Random

class MovieRepositoryImpl : MovieRepository {

    private val mainThreadHandler = Handler(Looper.getMainLooper())

    override fun getMovieList(
        executor: Executor,
        callback: (result: RepositoryResult<List<Movie>>) -> Unit
    ) {
        executor.execute {
            Thread.sleep(SLEEP_TIME)

            val isEverythingOk = Random.nextBoolean()
            if (isEverythingOk) {
                mainThreadHandler.post {
                    callback(
                        Success(getMovies())
                    )
                }
            } else {
                mainThreadHandler.post {
                    callback(
                        Error(RuntimeException(ERROR_MESSAGE))
                    )
                }
            }
        }
    }

    private fun getMovies(): List<Movie> {
        val localMoviesList = ArrayList<Movie>()
        for (i in 1..10) {
            localMoviesList.add(Movie(releaseDate = MovieDefaultValue.MOVIE_RELEASE_DATE + "# $i"))
        }
        return localMoviesList
    }

    override fun getMovieDetails(
        executor: Executor,
        callback: (result: RepositoryResult<Movie>) -> Unit
    ) {
        executor.execute {
            Thread.sleep(SLEEP_TIME)

            val isEverythingOk = Random.nextBoolean()
            if (isEverythingOk) {
                mainThreadHandler.post {
                    callback(
                        Success(Movie())
                    )
                }
            } else {
                mainThreadHandler.post {
                    callback(
                        Error(RuntimeException(ERROR_MESSAGE))
                    )
                }
            }
        }
    }

    companion object {
        private const val SLEEP_TIME: Long = 3000
        private const val ERROR_MESSAGE = "Can't load data, please reload"
    }
}

sealed class RepositoryResult<T>

data class Success<T>(val value: T) : RepositoryResult<T>()

data class Error<T>(val value: Throwable) : RepositoryResult<T>()
