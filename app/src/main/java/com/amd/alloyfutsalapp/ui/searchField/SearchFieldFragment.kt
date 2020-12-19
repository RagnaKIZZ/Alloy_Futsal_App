package com.amd.alloyfutsalapp.ui.searchField

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.amd.alloyfutsalapp.MainActivity
import com.amd.alloyfutsalapp.viewModel.MainViewModel
import com.amd.alloyfutsalapp.adapter.FieldAdapter
import com.amd.alloyfutsalapp.databinding.FragmentSearchFieldBinding
import com.amd.alloyfutsalapp.model.DataItem
import com.amd.alloyfutsalapp.model.ModelField
import com.amd.alloyfutsalapp.ui.fieldDetails.FieldDetailsActivity

class SearchFieldFragment : Fragment() {

    private val TAG = "SearchFieldFragment"
    private lateinit var viewModel: MainViewModel
    private var _binding: FragmentSearchFieldBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapterField: FieldAdapter

    private val args: SearchFieldFragmentArgs by navArgs()
    private lateinit var dataItem: ModelField

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = (activity as MainActivity).viewModel
        _binding = FragmentSearchFieldBinding.inflate(inflater, container, false)
        setRecyclerView()
        dataItem = args.modelField
        viewModel.apply {
            filteredList.observe(viewLifecycleOwner, Observer {
                if (it.isNotEmpty()) {
                    binding.txtNotFound.visibility = View.GONE
                } else {
                    binding.txtNotFound.visibility = View.VISIBLE
                }
                adapterField.differ.submitList(it)
            })
            setParamSearch("")
            setDataListItem(viewModel.getBookmarkList(dataItem.data))
        }
        setView()
        return binding.root
    }

    private fun setView() {
        binding.apply {

            imgBack.setOnClickListener {
                activity?.onBackPressed()
            }

            imgClear.setOnClickListener {
                this.edtSearch.setText("")
                imgClear.visibility = View.GONE
            }

            edtSearch.addTextChangedListener { editable ->
                editable?.let {
                    if (it.isNotEmpty()) {
                        this.imgClear.visibility = View.VISIBLE
                    } else {
                        this.imgClear.visibility = View.GONE
                    }
                    viewModel.setParamSearch(it.toString())
                } ?: kotlin.run {
                    this.imgClear.visibility = View.GONE
                }
            }
        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            viewModel.setDataListItem(viewModel.getBookmarkList(dataItem.data))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}