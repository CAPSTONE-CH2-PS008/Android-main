package com.ch2ps008.atomichabits.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.ch2ps008.atomichabits.R
import com.ch2ps008.atomichabits.databinding.ActivityLoginBinding
import com.ch2ps008.atomichabits.ui.main.MainActivity
import com.ch2ps008.atomichabits.ui.register.RegisterActivity
import com.ch2ps008.atomichabits.util.ViewModelFactory
import com.ch2ps008.atomichabits.response.Result
class LoginActivity : AppCompatActivity() {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.apply {
//            btnLogin.setOnClickListener {
//                val loginIntent = Intent(this@LoginActivity, MainActivity::class.java)
//                startActivity(loginIntent)
//                finish()
//            }
            tvRegister.setOnClickListener {
                val registerIntent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(registerIntent)
            }
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.edEmail.text.toString()
            val password = binding.edPassword.text.toString()

            loginUser(email, password)
        }
    }

    private fun loginUser(email: String, password: String) {
        loginViewModel.loginUser(email, password).observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressIndicator.visibility = View.VISIBLE
                }

                is Result.Success -> {
                    binding.progressIndicator.visibility = View.GONE
                    AlertDialog.Builder(this).apply {
                        setTitle(getString(R.string.sukses_login))
                        setMessage(getString(R.string.login_berhasil))
                        setPositiveButton(getString(R.string.lanjut)) { _, _ ->
                            val intent =
                                Intent(this@LoginActivity, MainActivity::class.java).apply {
                                    flags =
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                }
                            startActivity(intent)
                        }
                        show()
                    }
                }

                is Result.Error -> {
                    binding.progressIndicator.visibility = View.GONE
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

}