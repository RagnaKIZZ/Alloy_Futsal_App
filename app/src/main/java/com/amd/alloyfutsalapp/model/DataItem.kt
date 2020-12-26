package com.amd.alloyfutsalapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "field")
data class DataItem(

	@PrimaryKey
	@field:SerializedName("id_field")
	val idField: String,

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("operational_hour")
	val operationalHour: String,

	@field:SerializedName("name_field")
	val nameField: String,

	@field:SerializedName("facility")
	val facility: String,

	@field:SerializedName("imgSrc")
	val imgSrc: List<ImgSrcItem>,

	@field:SerializedName("field_type")
	val fieldType: List<FieldTypeItem>,

	@field:SerializedName("latLng")
	val latLng: String,

	@field:SerializedName("isIndoor")
	val isIndoor: String,

	@field:SerializedName("phone")
	val phone: String,

	@field:SerializedName("amount_field")
	val amountField: String,

	var isBookmarked: Boolean
) : Serializable