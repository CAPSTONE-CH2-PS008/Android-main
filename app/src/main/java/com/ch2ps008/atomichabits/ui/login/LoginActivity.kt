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
            tvRegister.setOnClickListener {
                val registerIntent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(registerIntent)
            }
            btnLogin.setOnClickListener {
                val email = binding.edEmail.text.toString()
                val password = binding.edPassword.text.toString()

//                loginUser(email, password)

                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

//    private fun loginUser(email: String, password: String) {
//        loginViewModel.loginUser(email, password).observe(this) { result ->
//            when (result) {
//                is Result.Loading -> {
//                    showLoading(true)
//                }
//
//                is Result.Success -> {
//                    showLoading(false)
//                    AlertDialog.Builder(this).apply {
//                        setTitle(getString(R.string.sukses_login))
//                        setMessage(getString(R.string.login_berhasil))
//                        setPositiveButton(getString(R.string.lanjut)) { _, _ ->
//                            val intent =
//                                Intent(this@LoginActivity, MainActivity::class.java)
//                            startActivity(intent)
//                        }
//                        show()
//                    }
//                }
//
//                is Result.Error -> {
//                    showLoading(false)
//                    AlertDialog.Builder(this).apply {
//                        setTitle(getString(R.string.error))
//                        setMessage(result.error)
//                        setPositiveButton(getString(R.string.ok)) { it, _ ->
//                            it.dismiss()
//                        }
//                    }.create().show()
//                }
//            }
//        }
//    }

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