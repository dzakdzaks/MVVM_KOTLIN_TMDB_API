package com.dzakdzaks.tmdb_mvvm_kotlin.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dzakdzaks.tmdb_mvvm_kotlin.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private var backPressedTimer: Long? = 0
    private var backToast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onBackPressed() {
        if (backPressedTimer!! + 2000 > System.currentTimeMillis()) {
            backToast?.cancel()
            super.onBackPressed()
            return
        } else {
            backToast = Toast.makeText(this, "Press back again to exit.", Toast.LENGTH_SHORT)
            backToast?.show()
        }

        backPressedTimer = System.currentTimeMillis()
    }
}
