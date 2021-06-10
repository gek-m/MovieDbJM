package com.example.moviedbjm.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedbjm.R
import com.example.moviedbjm.domain.Movie
import com.example.moviedbjm.domain.MovieRepositoryImpl
import com.example.moviedbjm.domain.Success
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val app: Application,
    private val repository: MovieRepositoryImpl
) : ViewModel() {

    //private val executor = Executors.newSingleThreadExecutor()

    private val _loading = MutableStateFlow(false)
    private val _error = MutableSharedFlow<String>()
    private val _movies = MutableStateFlow<List<Movie>>(listOf())

    val loading: Flow<Boolean> = _loading
    val error: Flow<String> = _error
    val movies: Flow<List<Movie>> = _movies

    fun fetchMovies() {

        _loading.value = true

        viewModelScope.launch {
            val result = repository.getMovieListSuspend()

            _loading.value = false

            when (result) {
                is Success -> {
                    _movies.value = result.value
                    //_error.emit("")
                }

                is Error -> {
                    _error.emit(app.getString(R.string.error_text))
                }
            }
        }
    }

    /*override fun onCleared() {
        super.onCleared()
        executor.shutdown()
    }*/
}