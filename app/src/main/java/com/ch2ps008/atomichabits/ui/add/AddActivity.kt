package com.ch2ps008.atomichabits.ui.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import com.ch2ps008.atomichabits.R
import com.ch2ps008.atomichabits.databinding.ActivityAddBinding
import com.ch2ps008.atomichabits.databinding.ActivityRegisterBinding

class AddActivity : AppCompatActivity() {

    private var _binding: ActivityAddBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateCustomActionBarTitle(getString(R.string.add_activity))

        binding.apply {
            edStartHour.filters = arrayOf(MinMaxEditTextInputFilter(0, 23))
            edEndHour.filters = arrayOf(MinMaxEditTextInputFilter(0,23))
        }
}

    class MinMaxEditTextInputFilter(private val mMin: Int, private val mMax: Int) : InputFilter {
        override fun filter(
            source: CharSequence,
            start: Int,
            end: Int,
            dest: Spanned,
            dstart: Int,
            dend: Int,
        ): CharSequence {
            try {
                val newValueString: String = dest.subSequence(0, dstart).toString() +
                        source.subSequence(start, end).toString() +
                        dest.subSequence(dend, dest.length)
                if (isInRange(mMin, mMax, newValueString.toInt()) && newValueString.length <= 2) {
                    return source
                }
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            }
            return ""
        }

        private fun isInRange(min: Int, max: Int, value: Int): Boolean {
            return if (max > min) {
                value in min..max
            } else {
                value in max..min
            }
        }
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