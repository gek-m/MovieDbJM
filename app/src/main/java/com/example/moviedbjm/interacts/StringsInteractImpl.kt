package com.example.moviedbjm.interacts

import android.content.Context
import com.example.moviedbjm.R

class StringsInteractImpl(private val context: Context): StringsInteract {
    override val errorStr = context.getString(R.string.error_text)
    override val reloadStr = context.getString(R.string.reload_text)
    override val apiLoadError = context.getString(R.string.api_load_error)
}