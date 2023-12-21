package com.ch2ps008.atomichabits.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.ch2ps008.atomichabits.R
import com.ch2ps008.atomichabits.databinding.ActivityRegisterBinding
import com.ch2ps008.atomichabits.databinding.ActivitySplashScreenBinding
import com.ch2ps008.atomichabits.ui.welcome.WelcomeActivity

class SplashScreenActivity : AppCompatActivity() {

    private var _binding: ActivitySplashScreenBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_SCREEN_DELAY_MS)
    }
    companion object {
        private const val SPLASH_SCREEN_DELAY_MS = 1500L
    }
}