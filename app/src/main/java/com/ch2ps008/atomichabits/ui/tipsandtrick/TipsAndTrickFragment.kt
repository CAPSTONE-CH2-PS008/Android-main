package com.ch2ps008.atomichabits.ui.tipsandtrick

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ch2ps008.atomichabits.R
import com.ch2ps008.atomichabits.databinding.FragmentTipsandtrickBinding
import com.ch2ps008.atomichabits.ui.main.MainActivity

class TipsAndTrickFragment : Fragment() {

    private var _binding: FragmentTipsandtrickBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_tipsandtrick, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).updateCustomActionBarTitle(getString(R.string.tips_and_trick))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}