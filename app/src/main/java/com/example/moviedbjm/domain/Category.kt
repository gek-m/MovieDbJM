package com.example.moviedbjm.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(val name: String, val movies: List<Movie>) : Parcelable