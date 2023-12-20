package com.ch2ps008.atomichabits.ui.habit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ch2ps008.atomichabits.R
import com.ch2ps008.atomichabits.adapter.HabitAdapter
import com.ch2ps008.atomichabits.adapter.PredictAdapter
import com.ch2ps008.atomichabits.databinding.ActivityProfileBinding
import com.ch2ps008.atomichabits.databinding.FragmentHabitBinding
import com.ch2ps008.atomichabits.databinding.FragmentTipsandtrickBinding
import com.ch2ps008.atomichabits.db.Habit
import com.ch2ps008.atomichabits.db.Predict
import com.ch2ps008.atomichabits.ui.list.ListViewModel
import com.ch2ps008.atomichabits.util.ViewModelFactory
import com.ch2ps008.atomichabits.response.Result
import com.google.android.material.snackbar.Snackbar

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
                        initAction()
                    }
                }
                2 -> {
                    listViewModel.getPredict().observe(viewLifecycleOwner){
                        setPredict(it)
                        initActionPredict()
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

        val adapter = HabitAdapter(habit)
        binding.rvHabit.adapter = adapter

        recycler.adapter = adapter
        adapter.submitList(habit)
    }
    private fun setPredict(habit: List<Predict>) {
        recycler = binding.rvHabit
        recycler.layoutManager = LinearLayoutManager(requireActivity())

        val adapter = PredictAdapter(habit)
        binding.rvHabit.adapter = adapter

        recycler.adapter = adapter
        adapter.submitList(habit)
    }

    private fun initAction() {
        val adapter = binding.rvHabit.adapter as HabitAdapter
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                return makeMovementFlags(0, ItemTouchHelper.RIGHT)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val habit = adapter.getHabitAt(position)

                listViewModel.deleteTask(habit)

                view?.let {
                    Snackbar.make(it, "Habit berhasil dihapus", Snackbar.LENGTH_LONG).apply {
                        setAction("Undo") {
                            listViewModel.undoHabit(habit)
                        }
                        show()
                    }
                }
            }
        })
        itemTouchHelper.attachToRecyclerView(recycler)
    }

    private fun initActionPredict() {
        val adapter = binding.rvHabit.adapter as PredictAdapter
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                return makeMovementFlags(0, ItemTouchHelper.RIGHT)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val predict = adapter.getPredictAt(position)

                listViewModel.deletePredict(predict)

                view?.let {
                    Snackbar.make(it, "Predict berhasil dihapus", Snackbar.LENGTH_LONG).apply {
                        setAction("Undo") {
                            listViewModel.undoPredict(predict)
                        }
                        show()
                    }
                }
            }
        })
        itemTouchHelper.attachToRecyclerView(recycler)
    }


    companion object {
        const val ARG_HABIT = "habit"
        const val ARG_POSITION = "section_number"
    }
}