package com.amd.alloyfutsalapp.db.converters

import androidx.room.TypeConverter
import com.amd.alloyfutsalapp.model.FieldTypeItem
import com.amd.alloyfutsalapp.model.ImgSrcItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class FieldConverter {

    @TypeConverter
    fun fromImgList(imgList: List<ImgSrcItem>): String {
        val gson = Gson()
        val type: Type = object : TypeToken<List<ImgSrcItem>>() {}.type
        return gson.toJson(imgList, type)
    }

    @TypeConverter
    fun toImgList(imgListString: String): List<ImgSrcItem> {
        val gson = Gson()
        val type: Type = object : TypeToken<List<ImgSrcItem>>() {}.type
        return gson.fromJson(imgListString, type)
    }

    @TypeConverter
    fun fromFieldType(fieldType: List<FieldTypeItem>): String {
        val gson = Gson()
        val type: Type = object : TypeToken<List<FieldTypeItem>>() {}.type
        return gson.toJson(fieldType, type)
    }

    @TypeConverter
    fun toFieldType(fieldType: String): List<FieldTypeItem> {
        val gson = Gson()
        val type: Type = object : TypeToken<List<FieldTypeItem>>() {}.type
        return gson.fromJson(fieldType, type)
    }

}