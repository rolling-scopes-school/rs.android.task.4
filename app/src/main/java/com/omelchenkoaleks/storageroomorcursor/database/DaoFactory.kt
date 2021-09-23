package com.omelchenkoaleks.storageroomorcursor.database

import android.content.Context
import androidx.preference.PreferenceManager
import androidx.room.Room
import com.omelchenkoaleks.storageroomorcursor.R
import com.omelchenkoaleks.storageroomorcursor.database.cursor.AnimalDBWithCursor
import com.omelchenkoaleks.storageroomorcursor.database.room.AnimalDao
import com.omelchenkoaleks.storageroomorcursor.database.room.AnimalDatabase

class DaoFactory(private val context: Context) {

    private val preference by lazy {
        PreferenceManager.getDefaultSharedPreferences(context)
    }

    private val db: AnimalDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            AnimalDatabase::class.java,
            "db_animals"
        ).build()

    private val room by lazy { db.animalDao() }
    private val cursor by lazy { AnimalDBWithCursor(context) }

    val animalDao: AnimalDao
        get() = if (whatIsDb()) room else cursor

    private fun whatIsDb() : Boolean {
        val isRoom = preference.getBoolean(context.getString(R.string.to_room), false)
        val isCursor = preference.getBoolean(context.getString(R.string.to_cursor), false)

        return when {
            isRoom -> true
            isCursor -> false
            else -> true
        }
    }
}