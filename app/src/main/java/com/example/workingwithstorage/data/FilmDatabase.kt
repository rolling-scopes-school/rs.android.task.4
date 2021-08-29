package com.example.workingwithstorage.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.workingwithstorage.model.Film
import kotlinx.coroutines.InternalCoroutinesApi

@Database(entities = [Film::class], version = 1, exportSchema = false)
abstract class FilmDatabase: RoomDatabase() {

    abstract fun filmDao(): FilmDao

    companion object{
        @Volatile
        private var INSTANCE : FilmDatabase? = null

        @InternalCoroutinesApi
        fun getDatabase (context: Context): FilmDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            kotlinx.coroutines.internal.synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FilmDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}