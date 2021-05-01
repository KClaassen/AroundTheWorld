package com.example.android.capstoneproject_aroundtheworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.bottom_nav_menu)

        val navController: NavController = findNavController(R.id.myNavHostFragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
                setOf(
                        R.id.countriesListFragment,
                        R.id.tripsListFragment
                )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

//        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_nav_menu)
//        bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

//    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
//        when (item.itemId) {
//            R.id.bottom_menu_countries -> {
//
//                return@OnNavigationItemSelectedListener true
//            }
//            R.id.bottom_menu_trips -> {
//
//                return@OnNavigationItemSelectedListener true
//            }
//        }
//        false
//    }
}