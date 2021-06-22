package com.example.moviedbjm.storage

import android.content.Context

class MovieStorage(private val context: Context) {

    companion object {
        const val ADULT_PREFERENCE = "adult_preference"
        const val IS_ADULT_KEY = "isAdult"
    }

    private val sharedPreferences =
        context.getSharedPreferences(ADULT_PREFERENCE, Context.MODE_PRIVATE)

    var isAdultFlag: Boolean
        get() = sharedPreferences.getBoolean(IS_ADULT_KEY, false)
        set(value) {
            sharedPreferences
                .edit()
                .putBoolean(IS_ADULT_KEY, value)
                .apply()
        }
}