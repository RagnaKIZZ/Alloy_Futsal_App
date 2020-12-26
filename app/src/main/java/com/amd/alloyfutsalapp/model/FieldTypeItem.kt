package com.amd.alloyfutsalapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FieldTypeItem(

	@field:SerializedName("type_field")
	val typeField: String,

	@field:SerializedName("price")
	val price: String
) : Serializable