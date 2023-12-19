package com.ch2ps008.atomichabits.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ch2ps008.atomichabits.R
import com.ch2ps008.atomichabits.adapter.HabitAdapter
import com.ch2ps008.atomichabits.adapter.HomeAdapter
import com.ch2ps008.atomichabits.databinding.FragmentHomeBinding
import com.ch2ps008.atomichabits.databinding.FragmentListBinding
import com.ch2ps008.atomichabits.ui.list.ListViewModel
import com.ch2ps008.atomichabits.ui.main.MainActivity
import com.ch2ps008.atomichabits.util.ViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
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

        adapter = HomeAdapter{

        }

        binding.rvActivity.adapter = adapter
        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvActivity.layoutManager = layoutManager

        setupAction()
    }

    private fun setupAction(){
        mainViewModel.getHabit().observe(viewLifecycleOwner) { habits ->
            adapter.submitList(habits)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}