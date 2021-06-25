package com.example.moviedbjm.ui.item

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedbjm.R
import com.example.moviedbjm.domain.Movie
import com.example.moviedbjm.domain.MovieRepository
import com.example.moviedbjm.domain.Success
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val app: Application,
    private val repository: MovieRepository
) : ViewModel() {

    //private val executor = Executors.newSingleThreadExecutor()

    private val _loading = MutableStateFlow(false)
    private val _error = MutableStateFlow<String?>(null)
    private val _movie = MutableStateFlow<Movie?>(null)

    val loading: StateFlow<Boolean> = _loading
    val error: StateFlow<String?> = _error
    val movie: StateFlow<Movie?> = _movie

    fun fetchMovie() {

        _loading.value = true

        viewModelScope.launch {

            when (val result = repository.getMovieDetailsSuspend()) {
                is Success -> {
                    _movie.value = result.value
                    _error.value = null
                }
                is Error -> {
                    _error.value = app.getString(R.string.api_load_error)
                }
            }

            _loading.value = false
        }
    }
}