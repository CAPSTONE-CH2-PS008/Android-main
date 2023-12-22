package com.ch2ps008.atomichabits.ui.profile

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.ch2ps008.atomichabits.R
import com.ch2ps008.atomichabits.databinding.ActivityProfileBinding
import com.ch2ps008.atomichabits.ui.welcome.WelcomeActivity
import com.ch2ps008.atomichabits.util.ViewModelFactory

class ProfileActivity : AppCompatActivity() {

    private var _binding: ActivityProfileBinding? = null
    private val binding get() = _binding!!

    private val profileViewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateCustomActionBarTitle(getString(R.string.profile))

        profileViewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            } else {
                binding.tvUsername.text = user.username
                binding.tvEmail.text = user.email
            }
        }

        binding.apply {
            btnLogout.setOnClickListener {
                logoutConfirmation()
            }
            btnChangeLanguage.setOnClickListener {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }
        }
    }

    private fun logoutConfirmation() {
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.confirmation))
            setMessage(getString(R.string.are_you_sure_want_to_logout))
            setPositiveButton(context.getString(R.string.yes)) { _, _ ->
                profileViewModel.logout()
                profileViewModel.deleteDB()
                finish()
            }
            setNegativeButton(getString(R.string.no), null)
        }.create().show()
    }

    private fun updateCustomActionBarTitle(title: String) {
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.customactionbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val customActionBarView = supportActionBar?.customView
        val titleTextView = customActionBarView?.findViewById<TextView>(R.id.tvTitle)
        titleTextView?.text = title
    }
}