package com.example.moviedbjm.ui.main

import android.app.Application
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedbjm.R
import com.example.moviedbjm.domain.Movie
import com.example.moviedbjm.domain.MovieRepositoryImpl
import com.example.moviedbjm.domain.Success
import com.example.moviedbjm.storage.MovieStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val app: Application,
    private val repository: MovieRepositoryImpl,
    private val movieStorage: MovieStorage
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    private val _error = MutableSharedFlow<String>()
    private val _movies = MutableStateFlow<List<Movie>>(listOf())
    private val _isAdultFlag = MutableStateFlow(movieStorage.isAdultFlag)

    val loading: Flow<Boolean> = _loading
    val error: Flow<String> = _error
    val movies: Flow<List<Movie>> = _movies
    val isAdultFlag: Flow<Boolean> = _isAdultFlag

    fun fetchMovies() {

        _loading.value = true

        if(_isAdultFlag.value) {
            Toast.makeText(app.applicationContext, "Adult", Toast.LENGTH_SHORT).show()
        }

        viewModelScope.launch {
            val result = repository.getMovieListSuspend(_isAdultFlag.value)

            _loading.value = false

            when (result) {
                is Success -> {
                    _movies.value = result.value
                }

                is Error -> {
                    _error.emit(app.getString(R.string.api_load_error))
                }
            }
        }
    }
}