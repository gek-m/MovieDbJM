package com.example.moviedbjm.ui.item

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviedbjm.R
import com.example.moviedbjm.domain.Movie
import com.example.moviedbjm.domain.MovieRepository
import com.example.moviedbjm.domain.Success
import java.util.concurrent.Executors

class DetailsViewFragment(
    private val app: Application,
    private val repository: MovieRepository
) : ViewModel() {

    private val executor = Executors.newSingleThreadExecutor()

    private val _loadingLiveData = MutableLiveData(false)
    private val _errorLiveData = MutableLiveData<String?>()
    private val _movieLiveData = MutableLiveData<Movie>()

    val loadingLiveData: LiveData<Boolean> = _loadingLiveData
    val errorLiveData: LiveData<String?> = _errorLiveData
    val movieLiveData: LiveData<Movie> = _movieLiveData

    fun fetchMovie() {

        _loadingLiveData.value = true

        repository.getMovieDetails(executor) {
            when (it) {
                is Success -> {
                    val result: Movie = it.value
                    _movieLiveData.value = result
                    _errorLiveData.value = null
                }

                is Error -> {
                    _errorLiveData.value = app.getString(R.string.error_text)
                }
            }

            _loadingLiveData.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        executor.shutdown()
    }
}