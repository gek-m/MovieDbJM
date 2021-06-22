package com.example.moviedbjm.storage

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MovieHistoryTable")
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val releaseDate: String,
    val voteAverage: Double,
    val posterPath: String,
    val overview: String,
    val adult: Boolean
)