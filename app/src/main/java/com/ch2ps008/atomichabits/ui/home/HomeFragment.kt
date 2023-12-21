package com.ch2ps008.atomichabits.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.ch2ps008.atomichabits.R
import com.ch2ps008.atomichabits.adapter.HomeAdapter
import com.ch2ps008.atomichabits.databinding.FragmentHomeBinding
import com.ch2ps008.atomichabits.ui.main.MainActivity
import com.ch2ps008.atomichabits.util.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.Locale

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private val mainViewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }


    private lateinit var adapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).updateCustomActionBarTitle(getString(R.string.title_home))

        adapter = HomeAdapter()

        binding.rvActivity.adapter = adapter
        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvActivity.layoutManager = layoutManager

        setupAction()
        setupRV()
    }

    private fun setupAction(){
        mainViewModel.getNearestHabit().observe(viewLifecycleOwner) { habit ->
            if (habit != null) {
                binding.tvNearestActivity.text = habit.activityName
                val activityCategories = resources.getStringArray(R.array.activity_array)
                val adjustedCategoryIndex = habit.activityCategory - 1
                val selectedCategory = activityCategories.getOrNull(adjustedCategoryIndex)
                binding.tvCategory.text = selectedCategory

                binding.tvDate.text = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(habit.creationDate)

                val formattedStartHour = String.format("%02d.00", habit.startHour)
                val formattedEndHour = String.format("%02d.00", habit.endHour)
                binding.tvTime.text =
                    getString(R.string.formathour, formattedStartHour, formattedEndHour)

            } else {
                binding.tvNearestActivity.text = getString(R.string.no_nearest_activity)
            }
        }
    }

    private fun setupRV() {
        mainViewModel.getHabit().observe(viewLifecycleOwner) { habits ->
            adapter.submitList(habits)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}