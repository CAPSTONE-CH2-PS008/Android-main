package com.ch2ps008.atomichabits.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.ch2ps008.atomichabits.R
import com.ch2ps008.atomichabits.databinding.ActivityLoginBinding
import com.ch2ps008.atomichabits.databinding.ActivityProfileBinding
import com.ch2ps008.atomichabits.ui.login.LoginActivity

class ProfileActivity : AppCompatActivity() {

    private var _binding: ActivityProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.show()
        supportActionBar?.title = "Profile"

        binding.apply {
            btnLogout.setOnClickListener {
                val logoutIntent = Intent(applicationContext,LoginActivity::class.java)
                startActivity(logoutIntent)
                finish()
            }
        }
    }
}