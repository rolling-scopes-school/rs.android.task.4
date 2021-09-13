package rs.android.task4.repository

import kotlinx.coroutines.flow.Flow

interface IAnimalDao {

    fun getAllSortByName(): Flow<List<Animal>>

    fun getAllSortByAge(): Flow<List<Animal>>

    fun getAllSortByBreed(): Flow<List<Animal>>

    suspend fun add(animal: Animal)

    suspend fun update(animal: Animal)

    suspend fun delete(animal: Animal)

    fun insertData(data: List<Animal>)
}