package com.amd.alloyfutsalapp.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.amd.alloyfutsalapp.R
import com.amd.alloyfutsalapp.databinding.BottomsheetErrorBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import retrofit2.HttpException
import java.io.IOException
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class Utility {
    private val TAG = "Utility"
    companion object {

        @SuppressLint("UseCompatLoadingForDrawables")
        fun loadImage(context: Context, url: String, img: ImageView) {
            Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(context.getDrawable(R.drawable.placeholder_img))
                .into(img)
        }

        fun showSnackBar(
            view: View,
            msg: String
        ) {
            Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).apply {
                show()
            }
        }

        fun showSnackBarWithAction(
            view: View,
            msg: String,
            title: String,
            listener: View.OnClickListener
        ) {
            Snackbar.make(view, msg, Snackbar.LENGTH_LONG).apply {
                setAction(title) {
                    listener.onClick(it)
                }
                show()
            }
        }

        fun KeRupiah(nominal: String): String {
            val hasil: String
            val localeID = Locale("in", "ID")
            val pattern = "#,###"
            val decimalFormat = NumberFormat.getNumberInstance(localeID) as DecimalFormat
            decimalFormat.applyPattern(pattern)
            hasil = "Rp " + decimalFormat.format(java.lang.Double.valueOf(nominal))
            return hasil
        }

        fun dialogConfirmation(
            context: Context?,
            onClickListenerPositive: View.OnClickListener
        ) {
            val bottomSheetDialog = BottomSheetDialog(context!!)
            val binding = BottomsheetErrorBinding.inflate(LayoutInflater.from(context))
            bottomSheetDialog.setContentView(binding.root)
            binding.apply {
                btnConfirm.setOnClickListener {
                    bottomSheetDialog.dismiss()
                    onClickListenerPositive.onClick(it)
                }
                btnTutup.setOnClickListener {
                    bottomSheetDialog.dismiss()
                    (context as Activity).finish()
                }
            }
            bottomSheetDialog.setCancelable(true)
            bottomSheetDialog.show()
        }

    }

}