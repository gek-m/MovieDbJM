package com.example.moviedbjm.network

import com.example.moviedbjm.BuildConfig
import com.example.moviedbjm.network.responses.TmdbCategory
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieTmdbMapApi {

    @GET("3/movie/{category}")
    fun getMovieList(
        @Path("category") category: String,
        @Query("api_key") key: String
    ): Call<TmdbCategory>

    @GET("3/movie/{category}?api_key=${BuildConfig.MOVIE_API_KEY}")
    suspend fun getApiMovieListSuspend(
        @Path("category") category: String
    ): TmdbCategory
}