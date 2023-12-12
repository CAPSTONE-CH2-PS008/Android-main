package com.ch2ps008.atomichabits.ui.welcome

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ch2ps008.atomichabits.databinding.ActivityWelcomeBinding
import com.ch2ps008.atomichabits.ui.login.LoginActivity
import com.ch2ps008.atomichabits.ui.register.RegisterActivity

class WelcomeActivity : AppCompatActivity() {

    private var _binding: ActivityWelcomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.apply {
            btnLogin.setOnClickListener {
                val loginIntent = Intent(this@WelcomeActivity, LoginActivity::class.java)
                startActivity(loginIntent)
            }
            btnRegister.setOnClickListener {
                val registerIntent = Intent(this@WelcomeActivity, RegisterActivity::class.java)
                startActivity(registerIntent)
            }
            textViewFlipper.setOnClickListener{
                textViewFlipper.showNext()
                viewFlipper.showNext()
            }
        }
    }
}