package com.ch2ps008.atomichabits.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.ch2ps008.atomichabits.R
import com.ch2ps008.atomichabits.databinding.ActivityRegisterBinding
import com.ch2ps008.atomichabits.response.Result
import com.ch2ps008.atomichabits.ui.login.LoginActivity
import com.ch2ps008.atomichabits.util.ViewModelFactory

class RegisterActivity : AppCompatActivity() {

    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding!!

    private val registerViewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.apply {
            tvSignIn.setOnClickListener {
                val loginIntent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(loginIntent)
                finish()
            }
            btnRegister.setOnClickListener {
                val name = binding.edName.text.toString()
                val email = binding.edEmail.text.toString()
                val password = binding.edPassword.text.toString()

                registerUser (name, email, password)
            }

        }
    }

    private fun registerUser(name: String, email: String, password: String) {
        registerViewModel.registerUser(name, email, password).observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                }

                is Result.Success -> {
                    showLoading(false)
                    AlertDialog.Builder(this).apply {
                        setTitle(getString(R.string.akun_dibuat))
                        setMessage(
                            getString(
                                R.string.akun_dengan_berhasil_dibuat_login_sekarang,
                                email
                            )
                        )
                        setPositiveButton(getString(R.string.lanjut)) { _, _ ->
                            val intent =
                                Intent(this@RegisterActivity, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        show()
                    }
                }

                is Result.Error -> {
                    showLoading(false)
                    AlertDialog.Builder(this).apply {
                        setTitle(getString(R.string.error))
                        setMessage(result.error)
                        setPositiveButton(getString(R.string.ok)) { it, _ ->
                            it.dismiss()
                        }
                    }.create().show()
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                progressIndicator.visibility = View.VISIBLE
            } else {
                progressIndicator.visibility = View.GONE
            }
        }
    }
}