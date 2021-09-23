package com.omelchenkoaleks.storageroomorcursor.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.omelchenkoaleks.storageroomorcursor.database.room.AnimalDao
import com.omelchenkoaleks.storageroomorcursor.model.Animal

@Database(entities = [Animal::class], version = 1, exportSchema = false)
abstract class AnimalDatabase: RoomDatabase() {
    abstract fun animalDao(): AnimalDao
}