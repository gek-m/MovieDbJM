package com.example.moviedbjm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moviedbjm.databinding.MainActivityBinding
import com.example.moviedbjm.router.MainRouter
import com.example.moviedbjm.router.RouterHolder
import com.google.android.material.bottomnavigation.BottomNavigationView

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
            }
            false
        }
    }

    /*private val br: BroadcastReceiver = NetworkChangeReceiver()

    override fun onStart() {
        super.onStart()

        IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION).also {
            this.registerReceiver(br, it)
        }
    }

    override fun onStop() {
        super.onStop()

        this.unregisterReceiver(br)
    }*/

    /*private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }*/
}