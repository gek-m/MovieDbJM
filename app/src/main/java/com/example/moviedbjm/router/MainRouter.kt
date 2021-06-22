package com.example.moviedbjm.router

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moviedbjm.R
import com.example.moviedbjm.ui.contacts.ContactsFragment
import com.example.moviedbjm.ui.item.DetailsFragment
import com.example.moviedbjm.ui.main.MainFragment
import com.example.moviedbjm.ui.map.MapsFragment
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

    fun openMaps() {
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.container, MapsFragment())
            .addToBackStack("MapsFragment")
            .commit()
    }
}
