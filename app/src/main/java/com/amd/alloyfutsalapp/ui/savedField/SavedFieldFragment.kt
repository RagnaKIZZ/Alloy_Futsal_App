package com.amd.alloyfutsalapp.ui.savedField

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.amd.alloyfutsalapp.MainActivity
import com.amd.alloyfutsalapp.adapter.FieldAdapter
import com.amd.alloyfutsalapp.viewModel.MainViewModel
import com.amd.alloyfutsalapp.databinding.FragmentSavedFieldBinding
import com.amd.alloyfutsalapp.ui.fieldDetails.FieldDetailsActivity

class SavedFieldFragment : Fragment() {

    private val TAG = "SavedFieldFragment"
    private lateinit var viewModel: MainViewModel
    private lateinit var adapterField: FieldAdapter
    private var _binding: FragmentSavedFieldBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = (activity as MainActivity).viewModel
        _binding = FragmentSavedFieldBinding.inflate(inflater, container, false)
        setRecyclerView()
        viewModel.getSavedList().observe(viewLifecycleOwner, Observer {
            adapterField.differ.submitList(it)
            if (it.isEmpty()) {
                binding.lnMsg.visibility = View.VISIBLE
            } else {
                binding.lnMsg.visibility = View.GONE
            }
        })
        return binding.root
    }

    private fun setRecyclerView() {
        adapterField = FieldAdapter(activity as MainActivity, 2)
        binding.incContent.rvContent.apply {
            adapter = adapterField
            layoutManager = LinearLayoutManager(activity)
            setPadding(0,50,0,0)
        }
        adapterField.setOnItemClickListener { i, dataItem ->
            kotlin.run {
                val intent = Intent(activity, FieldDetailsActivity::class.java)
                intent.putExtra("data", dataItem)
                Log.d(TAG, "setRecyclerView: $dataItem")
                startActivityForResult(intent, 91)
            }
        }
    }
}