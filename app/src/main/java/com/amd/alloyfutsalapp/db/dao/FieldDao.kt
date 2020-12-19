package com.amd.alloyfutsalapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.amd.alloyfutsalapp.model.DataItem

@Dao
interface FieldDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertField(item: DataItem) : Long

    @Delete
    suspend fun deleteField(item: DataItem)

    @Query("SELECT * FROM field ")
    fun getField() : LiveData<List<DataItem>>

    @Query("SELECT * FROM field WHERE idField = :id")
    fun isBookmark(id: String) : Int

}