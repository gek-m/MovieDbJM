package com.example.moviedbjm.domain.responses

import com.google.gson.annotations.SerializedName

data class TmdbCategory(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<TmdbMovie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)
