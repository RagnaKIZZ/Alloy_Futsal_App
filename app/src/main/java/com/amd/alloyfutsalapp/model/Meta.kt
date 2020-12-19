package com.amd.alloyfutsalapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Meta(

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("code")
	val code: Int

) : Serializable