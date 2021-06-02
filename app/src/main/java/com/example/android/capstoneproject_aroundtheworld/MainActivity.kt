package com.example.android.capstoneproject_aroundtheworld

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.Navigator
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.android.capstoneproject_aroundtheworld.authentication.AuthenticationActivity
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar()?.hide(); // hide the title bar
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

        back_button.setOnClickListener {
            onBackPressed()
        }

        logout_button.setOnClickListener {
            showPopup(it)
        }

    }

    fun showPopup(v: View) {
        val popup = PopupMenu(this, v, Gravity.END)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.overflow_menu, popup.menu)

        popup.setOnMenuItemClickListener {
            handleMenuSelection(it)
        }
        popup.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.overflow_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun handleMenuSelection(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Navigator.navigateToAuthenticationActivity(this)
                        } else {
                            Toast.makeText(
                                this,
                                R.string.error_failed_to_log_out,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                return true
            }
            else -> false
        }
    }

    object Navigator {
        fun navigateToAuthenticationActivity(activity: Activity) {
            activity.apply {
                startActivity(Intent(this, AuthenticationActivity::class.java))
                // Finish all activities
                finishAffinity()
            }
        }
    }

}