package com.malhotra.mynotes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.malhotra.mynotes.dao.NotesDao
import com.malhotra.mynotes.model.Notes

@Database(entities = [Notes :: class], version = 1, exportSchema = false)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun notesDao() : NotesDao

    companion object{
        @Volatile
        private var INSTANCE : NotesDatabase? = null

        fun getDatabaseInstance(context: Context) : NotesDatabase {

            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val roomDatabase = Room
                    .databaseBuilder(context, NotesDatabase::class.java, "Notes")
                    .build()
                INSTANCE = roomDatabase
                return roomDatabase
            }
        }
    }
}