package com.omelchenkoaleks.storageroomorcursor.database

import android.content.Context
import androidx.room.Room

class DaoFactory(private val context: Context) {
    private val db: AnimalDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            AnimalDatabase::class.java,
            "db_animals"
        ).build()

    private val room by lazy { db.animalDao() }

    val animalDao: AnimalDao
        get() = room
}