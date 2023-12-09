package com.ch2ps008.atomichabits.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ch2ps008.atomichabits.databinding.ActivityLoginBinding
import com.ch2ps008.atomichabits.ui.main.MainActivity
import com.ch2ps008.atomichabits.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.apply {
            btnLogin.setOnClickListener {
                val loginIntent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(loginIntent)
                finish()
            }
            tvRegister.setOnClickListener {
                val registerIntent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(registerIntent)
            }
        }
    }
}