package com.ch2ps008.atomichabits.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.ch2ps008.atomichabits.R
import com.ch2ps008.atomichabits.adapter.HabitAdapter
import com.ch2ps008.atomichabits.adapter.SectionsPagerAdapter
import com.ch2ps008.atomichabits.databinding.FragmentListBinding
import com.ch2ps008.atomichabits.db.Habit
import com.ch2ps008.atomichabits.ui.main.MainActivity
import com.ch2ps008.atomichabits.util.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var adapter: HabitAdapter

    private val mainViewModel by viewModels<ListViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    private lateinit var recycler: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).updateCustomActionBarTitle(getString(R.string.list_activity))

        val sectionsPagerAdapter = SectionsPagerAdapter(requireActivity())
        val viewPager: ViewPager2 = view.findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = view.findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()


        adapter = HabitAdapter {

        }
        binding.rvHabit.adapter = adapter

        val layoutManager = LinearLayoutManager(requireActivity())
        recycler = binding.rvHabit
        recycler.layoutManager = layoutManager

        initAction()
        setupAction()
    }

    private fun setupAction() {
        mainViewModel.getHabit().observe(viewLifecycleOwner) { habits ->
            adapter.submitList(habits)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initAction() {
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
                val habit = (viewHolder as HabitAdapter.ViewHolder).getHabit
                mainViewModel.deleteTask(habit)
            }
        })
        itemTouchHelper.attachToRecyclerView(recycler)
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2,
        )
    }
}