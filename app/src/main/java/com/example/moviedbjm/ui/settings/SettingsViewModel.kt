package com.example.moviedbjm.ui.settings

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedbjm.R
import com.example.moviedbjm.storage.MovieStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(private val app: Application, private val movieStorage: MovieStorage) :
    ViewModel() {

    private val _text = MutableStateFlow(app.getString(R.string.settings_fragment_title))

    private val _isAdultFlag = MutableStateFlow(movieStorage.isAdultFlag)

    val text: StateFlow<String> = _text
    val isAdultFlag: StateFlow<Boolean> = _isAdultFlag

    fun showSettings() {

        viewModelScope.launch {
            _text.value = app.getString(R.string.settings_fragment_title)
        }
    }

    fun isAdultFlagChange() {
        viewModelScope.launch {
            val valueToSet = movieStorage.isAdultFlag.not()
            movieStorage.isAdultFlag = valueToSet
            _isAdultFlag.value = valueToSet
        }
    }
}