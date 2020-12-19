package com.amd.alloyfutsalapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.amd.alloyfutsalapp.databinding.ItemFieldBinding
import com.amd.alloyfutsalapp.model.DataItem
import com.amd.alloyfutsalapp.utils.Constants.Companion.IMG_URL
import com.amd.alloyfutsalapp.utils.Utility
import java.io.Serializable

class FieldAdapter(private val context: Context, private val param: Int) :
    RecyclerView.Adapter<FieldAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemFieldBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            //put compare item with unique value
            return oldItem.idField == newItem.idField
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            //condition when item/object have a same value
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFieldBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = differ.currentList[position]
        val imgAdapter = ImgFieldAdapter(context, param)
        holder.binding.apply {
            txtTitle.text = data.nameField
            txtAddress.text = data.address
            if (param == 1) {
                val snapHelper = LinearSnapHelper()
                snapHelper.attachToRecyclerView(rvImg)
            }
            rvImg.apply {
                adapter = imgAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
            imgAdapter.differ.submitList(data.imgSrc)
            imgAdapter.setOnItemClickListener {
                onItemClickListener?.let { onItemClick ->
                    onItemClick(position, data)
                }
            }

            root.setOnClickListener {
                onItemClickListener?.let { onItemClick ->
                    onItemClick(position, data)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Int, DataItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (Int, DataItem) -> Unit) {
        onItemClickListener = listener
    }
}