package com.amd.alloyfutsalapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ModelField(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("meta")
	val meta: Meta
) : Serializable