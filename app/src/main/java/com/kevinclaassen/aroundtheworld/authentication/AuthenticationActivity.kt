package com.kevinclaassen.aroundtheworld.authentication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kevinclaassen.aroundtheworld.MainActivity
import com.kevinclaassen.aroundtheworld.R
import com.firebase.ui.auth.AuthMethodPickerLayout
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_authentication.*

class AuthenticationActivity : AppCompatActivity() {

    companion object {
        const val TAG = "AuthenticationActivity"
        const val SIGN_IN_RESULT_CODE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE) //will hide the title
        getSupportActionBar()?.hide() // hide the title bar
        setContentView(R.layout.activity_authentication)

        getstarted_button.setOnClickListener {
            motion_layout.transitionToStart()
            motion_layout.transitionToEnd()
            launchSignInFlow()
        }

    }

    private fun navigateToMainActivity() {
        // Navigate to Main Activity this will go to the fragment that has been set as home in navigation file
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SIGN_IN_RESULT_CODE) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                val currentUser = FirebaseAuth.getInstance().currentUser

                if (currentUser!!.isEmailVerified) {
                    // Redirect to user profile
                    // Successfully signed in user.
                    Log.i(TAG, "Successfully signed in user " +
                            "${FirebaseAuth.getInstance().currentUser?.displayName}!"
                    )
                    navigateToMainActivity()
                } else {
                    currentUser.sendEmailVerification()
                    Toast.makeText(this, "Check your email to verify your account", Toast.LENGTH_SHORT).show()
                }

            } else {
                // Sign in failed. If response is null the user canceled the sign-in flow using
                // the back button. Otherwise check response.getError().getErrorCode() and handle
                // the error.
                Toast.makeText(this, "Failed to login, please check your credentials", Toast.LENGTH_SHORT).show()
                Log.i(TAG, "Failed to login ${response?.error?.errorCode}")
                return
            }
        }
    }

    private fun launchSignInFlow() {
        // Give users the option to sign in / register with their email or Google account. If users
        // choose to register with their email, they will need to create a password as well.
        val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build(), AuthUI.IdpConfig.GoogleBuilder().build()
        )

        // You must provide a custom layout XML resource and configure at least one
        // provider button ID. It's important that that you set the button ID for every provider
        // that you have enabled.
        val customLayout = AuthMethodPickerLayout.Builder(R.layout.custom_login_layout)
                .setGoogleButtonId(R.id.google_login)
                .setEmailButtonId(R.id.email_login)
                .build()

        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTheme(R.style.AppThemeFirebaseAuth)
                        .setAuthMethodPickerLayout(customLayout)
                        .setIsSmartLockEnabled(false)
                        .build(), SIGN_IN_RESULT_CODE
        )
    }
}