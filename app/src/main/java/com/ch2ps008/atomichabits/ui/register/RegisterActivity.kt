package com.ch2ps008.atomichabits.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ch2ps008.atomichabits.databinding.ActivityRegisterBinding
import com.ch2ps008.atomichabits.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.apply {
            btnRegister.setOnClickListener {
                val registerIntent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(registerIntent)
                finish()
            }
            tvSignIn.setOnClickListener {
                val loginIntent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(loginIntent)
                finish()
            }
        }
    }
}