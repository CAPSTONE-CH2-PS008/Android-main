package com.ch2ps008.atomichabits.ui.habit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ch2ps008.atomichabits.adapter.HabitAdapter
import com.ch2ps008.atomichabits.databinding.FragmentHabitBinding
import com.ch2ps008.atomichabits.db.Habit
import com.ch2ps008.atomichabits.db.Predict
import com.ch2ps008.atomichabits.ui.list.ListViewModel
import com.ch2ps008.atomichabits.util.ViewModelFactory
import com.ch2ps008.atomichabits.adapter.PredictAdapter

class HabitFragment : Fragment() {

    private var _binding: FragmentHabitBinding? = null
    private val binding get() = _binding!!

    private val listViewModel by viewModels<ListViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }


    private lateinit var recycler: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHabitBinding.bind(view)

        val position = arguments?.getInt(ARG_POSITION)

        binding.apply {
            when (position) {
                1 -> {
                    listViewModel.getHabit().observe(viewLifecycleOwner){
                        setData(it)
                    }
                }
                2 -> {
                    listViewModel.getPredict().observe(viewLifecycleOwner){
                        setPredict(it)
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setData(habit: List<Habit>) {
        recycler = binding.rvHabit
        recycler.layoutManager = LinearLayoutManager(requireActivity())

        val adapter = HabitAdapter(listViewModel.getHabitDao(), listViewModel.getPredictDao())
        adapter.submitList(habit)
        binding.rvHabit.adapter = adapter

        recycler.adapter = adapter
        adapter.submitList(habit)
    }
    private fun setPredict(habit: List<Predict>) {
        recycler = binding.rvHabit
        recycler.layoutManager = LinearLayoutManager(requireActivity())

        val adapter = PredictAdapter(listViewModel.getPredictDao())
        binding.rvHabit.adapter = adapter

        recycler.adapter = adapter
        adapter.submitList(habit)
    }

    companion object {
        const val ARG_HABIT = "habit"
        const val ARG_POSITION = "section_number"
    }
}