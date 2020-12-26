package com.amd.alloyfutsalapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.amd.alloyfutsalapp.databinding.ItemImgDetailsBinding
import com.amd.alloyfutsalapp.model.ImgSrcItem
import com.amd.alloyfutsalapp.utils.Constants.Companion.IMG_URL
import com.amd.alloyfutsalapp.utils.Utility

class DetailPagerAdapter(val context: Context, val list: List<ImgSrcItem>) : PagerAdapter() {
    @SuppressLint("SetTextI18n")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = ItemImgDetailsBinding.inflate(inflater, container, false)
        Utility.loadImage(context, IMG_URL+list[position].imgSrc, view.imgField)
        container.addView(view.root)
        return view.root
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}