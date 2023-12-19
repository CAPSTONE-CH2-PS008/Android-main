package com.ch2ps008.atomichabits.ui.tipsandtrick

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ch2ps008.atomichabits.R
import com.ch2ps008.atomichabits.adapter.TipsAndTrickAdapter
import com.ch2ps008.atomichabits.databinding.FragmentTipsandtrickBinding
import com.ch2ps008.atomichabits.source.TipsAndTrickData
import com.ch2ps008.atomichabits.ui.main.MainActivity
import com.ch2ps008.atomichabits.ui.register.RegisterViewModel
import com.ch2ps008.atomichabits.util.ViewModelFactory
import java.util.ArrayList

class TipsAndTrickFragment : Fragment() {

    private var _binding: FragmentTipsandtrickBinding? = null
    private val binding get() = _binding!!

    private val tipsAndTrickViewModel by viewModels<TipsAndTrickViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    private val tipsAdapter by lazy { TipsAndTrickAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_tipsandtrick, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTipsandtrickBinding.bind(view)

        binding.rvTips.adapter = tipsAdapter
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvTips.layoutManager = layoutManager

        tipsAndTrickViewModel.getTips().observe(viewLifecycleOwner) { tips ->
            tipsAdapter.updateData(tips)
        }

        (requireActivity() as MainActivity).updateCustomActionBarTitle(getString(R.string.tips_and_trick))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}