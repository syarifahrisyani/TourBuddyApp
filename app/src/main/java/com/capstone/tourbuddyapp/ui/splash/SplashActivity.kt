package com.capstone.tourbuddyapp.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import com.capstone.tourbuddyapp.R
import com.capstone.tourbuddyapp.ui.onboarding.OnBoardActivity

class SplashActivity : AppCompatActivity() {

    private val SPLASH_DELAY: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed( {
            val onBoardIntent = Intent(this@SplashActivity, OnBoardActivity::class.java)
            startActivity(onBoardIntent)
            finish()
        }, SPLASH_DELAY)
    }
}