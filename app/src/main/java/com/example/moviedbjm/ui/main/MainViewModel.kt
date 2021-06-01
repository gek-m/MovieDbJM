package com.example.moviedbjm.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviedbjm.R
import com.example.moviedbjm.domain.Movie
import com.example.moviedbjm.domain.MovieRepositoryImpl
import com.example.moviedbjm.domain.Success
import java.util.concurrent.Executors

class MainViewModel(
    private val app: Application,
    private val repository: MovieRepositoryImpl
) : ViewModel() {

    private val executor = Executors.newSingleThreadExecutor()

    private val _loadingLiveData = MutableLiveData(false)
    private val _errorLiveData = MutableLiveData<String?>()
    private val _moviesLiveData = MutableLiveData<List<Movie>>()

    val loadingLiveData: LiveData<Boolean> = _loadingLiveData
    val errorLiveData: LiveData<String?> = _errorLiveData
    val moviesLiveData: LiveData<List<Movie>> = _moviesLiveData

    fun fetchMovies() {

        _loadingLiveData.value = true

        repository.getMovieList(executor) {
            when (it) {
                is Success -> {
                    val result: List<Movie> = it.value
                    _moviesLiveData.value = result
                    _errorLiveData.value = null
                }

                is Error -> {
                    _errorLiveData.value = app.getString(R.string.error_text)
                }
            }

            _loadingLiveData.value = false
        }
    }
}