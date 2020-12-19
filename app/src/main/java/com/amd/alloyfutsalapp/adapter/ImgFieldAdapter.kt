package com.amd.alloyfutsalapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.amd.alloyfutsalapp.databinding.ItemImgBinding
import com.amd.alloyfutsalapp.databinding.ItemImgMiniBinding
import com.amd.alloyfutsalapp.model.DataItem
import com.amd.alloyfutsalapp.model.ImgSrcItem
import com.amd.alloyfutsalapp.utils.Constants
import com.amd.alloyfutsalapp.utils.Utility

class ImgFieldAdapter(private val context: Context, private val param: Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ViewHolder(val binding: ItemImgBinding) : RecyclerView.ViewHolder(binding.root)
    inner class ViewHolderMini(val bindingMini: ItemImgMiniBinding) : RecyclerView.ViewHolder(bindingMini.root)

    private val differCallback = object : DiffUtil.ItemCallback<ImgSrcItem>() {
        override fun areItemsTheSame(oldItem: ImgSrcItem, newItem: ImgSrcItem): Boolean {
            //put compare item with unique value
            return oldItem.idImg == newItem.idImg
        }

        override fun areContentsTheSame(oldItem: ImgSrcItem, newItem: ImgSrcItem): Boolean {
            //condition when item/object have a same value
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (param == 1) {
            val view = ItemImgBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(view)
        } else {
            val view =
                ItemImgMiniBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolderMini(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val data = differ.currentList[position]
                holder.binding.apply {
                    Utility.loadImage(context, Constants.IMG_URL + data.imgSrc, imgField)
                    root.setOnClickListener {
                        onItemClickListener?.let {
                            it(position)
                        }
                    }
                }
            }
            is ViewHolderMini -> {
                val data = differ.currentList[position]
                holder.bindingMini.apply {
                    Utility.loadImage(context, Constants.IMG_URL + data.imgSrc, imgField)
                    root.setOnClickListener {
                        onItemClickListener?.let {
                            it(position)
                        }
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

}