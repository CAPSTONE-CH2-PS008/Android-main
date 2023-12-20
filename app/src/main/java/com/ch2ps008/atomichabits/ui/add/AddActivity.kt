package com.ch2ps008.atomichabits.ui.add

import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.ch2ps008.atomichabits.R
import com.ch2ps008.atomichabits.databinding.ActivityAddBinding
import com.ch2ps008.atomichabits.response.Result
import com.ch2ps008.atomichabits.util.ViewModelFactory

class AddActivity : AppCompatActivity() {

    private var _binding: ActivityAddBinding? = null
    private val binding get() = _binding!!

    private val addViewModel by viewModels<AddViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateCustomActionBarTitle(getString(R.string.add_activity))

        dropdownActivity()
        dropdownBobot()
        dropdownInterest()

        binding.apply {
            edStartHour.filters = arrayOf(MinMaxEditTextInputFilter(0, 23))
            edEndHour.filters = arrayOf(MinMaxEditTextInputFilter(0, 23))
            btnSubmit.setOnClickListener {
                binding.apply {
                    val activityName = edActivityName.text.toString()
                    val bobot = spinnerBobot.selectedItemPosition
                    val activityCategory = spinnerActivity.selectedItemPosition
                    val startHour = edStartHour.text.toString().toIntOrNull()
                    val endHour = edEndHour.text.toString().toIntOrNull()
                    val interest = spinnerInterest.selectedItemPosition

                    if (activityName.isEmpty() || startHour == null || endHour == null) {
                        Toast.makeText(
                            this@AddActivity,
                            "Data harus diisi dengan lengkap",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        addViewModel.addHabit(
                            activityName,
                            bobot,
                            activityCategory,
                            startHour,
                            endHour,
                            interest
                        )
                        predict(bobot, activityCategory, startHour, endHour, interest)
                    }
                }

            }
        }
    }

    private fun predict(
        Bobot: Int,
        Activity: Int,
        Start_Time: Int,
        End_Time: Int,
        Interest: Int
    ) {
        addViewModel.postPredict(Bobot, Activity, Start_Time, End_Time, Interest)
            .observe(this) { result ->
                when (result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }

                    is Result.Success -> {
                        showLoading(false)
                        saveResult(result.data.result)
                        finish()
                    }

                    is Result.Error -> {
                        showLoading(false)
                    }
                }
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

    private fun dropdownActivity() {
        val spinnerActivity = binding.spinnerActivity

        val days = resources.getStringArray(R.array.activity_array)
        val dayValues = arrayOf(1, 2, 3, 4)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, days)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerActivity.adapter = adapter

        spinnerActivity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>,
                selectedItemView: android.view.View?,
                position: Int,
                id: Long
            ) {
                val selectedDayValue = dayValues[position]
                Log.d("priority", selectedDayValue.toString())
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
            }
        }
    }

    private fun dropdownInterest() {
        val spinnerInterest = binding.spinnerInterest

        val days = resources.getStringArray(R.array.interest_array)
        val dayValues = arrayOf(-3,-2,-1,0,1,2,3)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, days)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerInterest.adapter = adapter

        spinnerInterest.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                val selectedDayValue = dayValues[position]
                Log.d("interest", selectedDayValue.toString())
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
            }
        }
    }

    private fun dropdownBobot() {
        val spinnerBobot = binding.spinnerBobot

        val bobot = resources.getStringArray(R.array.bobot_array)
        val bobotValues = arrayOf(1, 2, 3)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, bobot)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerBobot.adapter = adapter

        spinnerBobot.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                val selectedBobotValue = bobotValues[position]
                Log.d("bobot", selectedBobotValue.toString())
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun saveResult(result: Int){
        addViewModel.saveResult(result)
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