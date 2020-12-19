package com.amd.alloyfutsalapp

import com.amd.alloyfutsalapp.api.RetrofitInstance
import com.amd.alloyfutsalapp.db.FieldDatabases
import com.amd.alloyfutsalapp.model.DataItem

class FieldRepo(private val db: FieldDatabases) {

    suspend fun getField() = RetrofitInstance.api.getField()

    fun getSavedField() = db.fieldDao().getField()

    suspend fun saveField(dataItem: DataItem) = db.fieldDao().upsertField(dataItem)

    suspend fun delField(dataItem: DataItem) = db.fieldDao().deleteField(dataItem)

    fun getItem(id: String) = db.fieldDao().isBookmark(id)

}