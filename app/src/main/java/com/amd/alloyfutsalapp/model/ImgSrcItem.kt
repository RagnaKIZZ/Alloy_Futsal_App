package com.amd.alloyfutsalapp.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ImgSrcItem(

    @field:SerializedName("id_img")
    val idImg: String,

    @field:SerializedName("img_src")
    val imgSrc: String

) : Serializable