package com.example.moviedbjm.router

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.moviedbjm.R
import com.example.moviedbjm.ui.contacts.ContactsFragment
import com.example.moviedbjm.ui.item.DetailsFragment
import com.example.moviedbjm.ui.main.MainFragment
import com.example.moviedbjm.ui.settings.SettingsFragment

class MainRouter(private val activity: AppCompatActivity) {

    fun openMovieList() {
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment())
            .commit()
    }

    fun openMovieDetails(bundle: Bundle) {
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.container, DetailsFragment.newInstance(bundle))
            .addToBackStack("MovieDetailsFragment")
            .commit()
    }

    fun openSettings() {
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.container, SettingsFragment())
            .addToBackStack("SettingsDetailsFragment")
            .commit()
    }

    fun openContacts() {
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.container, ContactsFragment())
            .addToBackStack("ContractsDetailsFragment")
            .commit()
    }
}
