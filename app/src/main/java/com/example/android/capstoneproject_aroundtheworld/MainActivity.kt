package com.example.android.capstoneproject_aroundtheworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_nav_menu)
        bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.bottom_menu_countries -> {

                return@OnNavigationItemSelectedListener true
            }
            R.id.bottom_menu_trips -> {

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
}