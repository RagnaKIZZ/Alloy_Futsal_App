package com.amd.alloyfutsalapp.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.amd.alloyfutsalapp.MainActivity
import com.amd.alloyfutsalapp.viewModel.MainViewModel
import com.amd.alloyfutsalapp.R
import com.amd.alloyfutsalapp.adapter.FieldAdapter
import com.amd.alloyfutsalapp.databinding.FragmentHomeBinding
import com.amd.alloyfutsalapp.model.ModelField
import com.amd.alloyfutsalapp.ui.fieldDetails.FieldDetailsActivity
import com.amd.alloyfutsalapp.utils.DataState
import com.amd.alloyfutsalapp.utils.Utility

class HomeFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapterField: FieldAdapter
    private val TAG = "HomeFragment"
    private var modelField: ModelField? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = (activity as MainActivity).viewModel
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setRecyclerView()
        setView()
        return binding.root
    }

    private fun setView() {
        viewModel.itemField.observe(viewLifecycleOwner, Observer { response ->

            when (response) {

                is DataState.Loading -> {
                    stateLoading()
                    Log.d(TAG, "setView: loading")
                }

                is DataState.Success -> {
                    hideLoading()
                    Log.d(TAG, "setView: success")
                    response.data?.let { resultField ->
                        val listItem = viewModel.getBookmarkList(resultField.data)
                        adapterField.differ.submitList(listItem)
                        modelField = resultField
                    }
                }

                is DataState.Error -> {
                    hideLoading()
                    Log.d(TAG, "setView: error")
                    showErrorDialog()
                }

                is DataState.NotInternet -> {
                    hideLoading()
                    Log.d(TAG, "setView: no internet")
                    showErrorDialog()
                }

                is DataState.Error400Above -> {
                    hideLoading()
                    Log.d(TAG, "setView: ${response.errorBody.toString()}")
                    showErrorDialog()
                }

            }

        })

        binding.fabSearch.setOnClickListener {
            modelField?.let {
                setBundle()
                Log.d(TAG, "setView: ${modelField.toString()}")
            }
        }

        binding.rlSearch.setOnClickListener {
            modelField?.let {
                setBundle()
                Log.d(TAG, "setView: ${modelField.toString()}")
            }
        }
    }

    private fun stateLoading() {
        binding.incContent.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.incContent.progressBar.visibility = View.GONE
    }

    private fun setRecyclerView() {
        adapterField = FieldAdapter(activity as MainActivity, 1)
        binding.incContent.rvContent.apply {
            adapter = adapterField
            layoutManager = LinearLayoutManager(activity)
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

    private fun showErrorDialog(){
        Utility.dialogConfirmation(activity){
            viewModel.loadDataItems()
        }
    }

    private fun setBundle() {
        val bundle = Bundle().apply {
            putSerializable("modelField", modelField)
        }

        findNavController().navigate(
            R.id.action_navigation_home_to_searchFieldFragment,
            bundle
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            setView()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}