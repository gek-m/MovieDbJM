package com.example.moviedbjm

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.moviedbjm.databinding.MainActivityBinding
import com.example.moviedbjm.router.MainRouter
import com.example.moviedbjm.router.RouterHolder
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(R.layout.main_activity), RouterHolder {

    override val router = MainRouter(this)

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            router.openMovieList()
        }

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.bottomNavigation

        navView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    router.openMovieList()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_settings -> {
                    router.openSettings()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_contacts -> {
                    if (ContextCompat.checkSelfPermission(
                            applicationContext,
                            Manifest.permission.READ_CONTACTS
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        router.openContacts()
                    } else {
                        if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
                            Snackbar.make(
                                findViewById<View>(R.id.content).rootView,
                                getString(R.string.request_permission_text),
                                Snackbar.LENGTH_INDEFINITE
                            ).setAction(
                                getString(R.string.grant_permission)
                            ) {
                                permissionRequest.launch(Manifest.permission.READ_CONTACTS)
                            }.show()
                        } else {
                            permissionRequest.launch(Manifest.permission.READ_CONTACTS)
                        }
                    }
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_map -> {
                    router.openMaps()
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }

    private val permissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                router.openContacts()
            } else {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.denied_permission_text),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
}