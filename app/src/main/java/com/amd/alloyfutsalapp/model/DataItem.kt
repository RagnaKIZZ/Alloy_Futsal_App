package com.amd.alloyfutsalapp.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.ParcelField
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "field")
data class DataItem(

	@PrimaryKey
	@field:SerializedName("id_field")
	val idField: String,

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("name_field")
	val nameField: String,

	@field:SerializedName("imgSrc")
	val imgSrc: List<ImgSrcItem>,

	@field:SerializedName("latLng")
	val latLng: String,

	@field:SerializedName("amount_field")
	val amountField: String,

	@field:SerializedName("price")
	val price: String,

	@field:SerializedName("operational_hour")
	val operationalHour: String,

	@field:SerializedName("facility")
	val facility: String,

	@field:SerializedName("isBookmark")
	var isBookmarked: Boolean

) : Serializable