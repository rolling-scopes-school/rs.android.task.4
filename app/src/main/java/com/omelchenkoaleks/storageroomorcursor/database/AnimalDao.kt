package com.omelchenkoaleks.storageroomorcursor.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.omelchenkoaleks.storageroomorcursor.model.Animal

@Dao
interface AnimalDao {

    @Query("SELECT * FROM table_animal ORDER BY CASE WHEN :order = 'name' THEN name END, CASE WHEN :order = 'age' THEN age END, CASE WHEN :order = 'breed' THEN breed END")
    fun getAnimalsOrderBy(order: String): LiveData<List<Animal>>

    @Query("SELECT * FROM table_animal WHERE id=(:id)")
    fun getAnimals(id: Int): LiveData<Animal?>

    @Insert
    suspend fun addAnimal(animal: Animal)

    @Update
    suspend fun updateAnimal(animal: Animal)

    @Delete
    suspend fun deleteAnimal(animal: Animal)

}