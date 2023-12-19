package com.ch2ps008.atomichabits.ui.tipsandtrick

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import com.ch2ps008.atomichabits.R
import com.ch2ps008.atomichabits.databinding.ActivityDetailTipsBinding
import com.ch2ps008.atomichabits.source.TipsAndTrick
import com.ch2ps008.atomichabits.util.loadImage
import java.util.TimeZone

class DetailTipsActivity : AppCompatActivity() {

    private var _binding: ActivityDetailTipsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailTipsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateCustomActionBarTitle(getString(R.string.tips_and_trick))

        setData()
    }

    private fun setData() {
        @Suppress("DEPRECATION")
        val tips = intent.getParcelableExtra<TipsAndTrick>(DETAIL_TIPS) as TipsAndTrick
        loadImage(applicationContext, tips.photoUrl, binding.ivTips)
        binding.tvTipsTitle.text = tips.title
        binding.tvDescription.text = tips.description
        val sourceText = getString(R.string.source, tips.source)
        binding.tvSource.text = sourceText
    }

    private fun updateCustomActionBarTitle(title: String) {
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.customactionbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val customActionBarView = supportActionBar?.customView
        val titleTextView = customActionBarView?.findViewById<TextView>(R.id.tvTitle)
        titleTextView?.text = title
    }

    companion object {
        const val DETAIL_TIPS = "detail_tips"
    }
}