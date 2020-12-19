package com.amd.alloyfutsalapp.ui.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.amd.alloyfutsalapp.MainActivity
import com.amd.alloyfutsalapp.viewModel.MainViewModel
import com.amd.alloyfutsalapp.databinding.FragmentInfoBinding

class InfoFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private var _binding : FragmentInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = (activity as MainActivity).viewModel
        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}