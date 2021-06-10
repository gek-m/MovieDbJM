package com.example.moviedbjm.domain

import android.os.Handler
import android.os.Looper
import com.example.moviedbjm.BuildConfig
import com.example.moviedbjm.network.responses.TmdbCategory
import com.example.moviedbjm.network.responses.parseToMovieList
import com.google.gson.Gson
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executor
import kotlin.random.Random

class MovieRepositoryConnection: MovieRepository {

    companion object {
        private const val SLEEP_TIME: Long = 100
        private const val ERROR_MESSAGE = "Can't load data, please reload"
        private const val READ_TIME_OUT: Int = 30_000
    }

    private val mainThreadHandler = Handler(Looper.getMainLooper())

    override fun getMovieList(
        executor: Executor,
        callback: (result: RepositoryResult<List<Movie>>) -> Unit
    ) {
        executor.execute {
            val url =
                URL("${BuildConfig.BASE_URL}3/movie/popular?api_key=${BuildConfig.MOVIE_API_KEY}")

            val connection = url.openConnection() as HttpURLConnection
            val gson = Gson()

            try {
                with(connection) {
                    requestMethod = "GET"
                    readTimeout = READ_TIME_OUT

                    val response = gson.fromJson(
                        inputStream.bufferedReader(),
                        TmdbCategory::class.java
                    )

                    mainThreadHandler.post {
                        callback.invoke(Success(response.parseToMovieList()))
                    }

                }
            } catch (ex: Exception) {
                mainThreadHandler.post {
                    callback.invoke(Error(ex))
                }
            } finally {
                connection.disconnect()
            }
        }
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

    override suspend fun getMovieListSuspend(): RepositoryResult<List<Movie>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieDetailsSuspend(): RepositoryResult<Movie> {
        TODO("Not yet implemented")
    }
}