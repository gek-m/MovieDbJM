package com.example.moviedbjm.domain

import android.os.Handler
import android.os.Looper
import com.example.moviedbjm.BuildConfig
import com.example.moviedbjm.network.CallException
import com.example.moviedbjm.network.MovieTmdbMapApi
import com.example.moviedbjm.network.responses.TmdbCategory
import com.example.moviedbjm.network.responses.parseToMovieList
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executor
import kotlin.random.Random

class MovieRepositoryImpl : MovieRepository {

    companion object {
        private const val SLEEP_TIME: Long = 100
        private const val ERROR_MESSAGE = "Can't load data, please reload"
        private const val API_POPULAR_CAT = "popular"
    }

    private val mainThreadHandler = Handler(Looper.getMainLooper())


    override fun getMovieList(
        executor: Executor,
        callback: (result: RepositoryResult<List<Movie>>) -> Unit
    ) {
        val gson = Gson()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        val mapApi: MovieTmdbMapApi = retrofit.create(MovieTmdbMapApi::class.java)

        mapApi.getMovieList(API_POPULAR_CAT, BuildConfig.MOVIE_API_KEY).enqueue(
            object : retrofit2.Callback<TmdbCategory> {
                override fun onResponse(
                    call: Call<TmdbCategory>,
                    response: Response<TmdbCategory>
                ) {
                    if (response.isSuccessful) {

                        response.body()?.let { movieResponse ->
                            val movieList = movieResponse.parseToMovieList()
                            callback.invoke(Success(movieList))
                        }

                    } else {
                        callback.invoke(Error(CallException(response.code())))
                    }
                }

                override fun onFailure(call: Call<TmdbCategory>, t: Throwable) {
                    callback.invoke(Error(t))
                }
            }
        )
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
        val gson = Gson()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        val mapApi: MovieTmdbMapApi = retrofit.create(MovieTmdbMapApi::class.java)

        try {
            val movieMapResponse = mapApi.getApiMovieListSuspend(API_POPULAR_CAT)
            return Success(movieMapResponse.parseToMovieList())

        } catch (ex: Exception) {
            return Error(ex)
        }

    }

    override suspend fun getMovieDetailsSuspend(): RepositoryResult<Movie> {
        return Success(Movie())
    }
}

sealed class RepositoryResult<T>

data class Success<T>(val value: T) : RepositoryResult<T>()

data class Error<T>(val value: Throwable) : RepositoryResult<T>()
