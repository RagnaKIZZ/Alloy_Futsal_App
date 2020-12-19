package com.amd.alloyfutsalapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.amd.alloyfutsalapp.db.converters.FieldConverter
import com.amd.alloyfutsalapp.db.dao.FieldDao
import com.amd.alloyfutsalapp.model.DataItem

@Database(entities = [DataItem::class], version = 2)
@TypeConverters(FieldConverter::class)

abstract class FieldDatabases : RoomDatabase() {

    abstract fun fieldDao(): FieldDao

    companion object {

        @Volatile
        private var instance: FieldDatabases? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                FieldDatabases::class.java,
                "field.db"
            ).fallbackToDestructiveMigration().build()
    }

}