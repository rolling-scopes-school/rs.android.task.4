package com.omelchenkoaleks.storageroomorcursor.database

import androidx.lifecycle.LiveData
import com.omelchenkoaleks.storageroomorcursor.database.room.AnimalDao
import com.omelchenkoaleks.storageroomorcursor.model.Animal

class AnimalsRepository private constructor(daoFactory: DaoFactory) {
    private var animalDao: AnimalDao = daoFactory.animalDao

    fun getAnimals(order: String): LiveData<List<Animal>> =
        animalDao.getAnimalsOrderBy(order)

    fun getAnimals(id: Int) : LiveData<Animal?> = animalDao.getAnimals(id)

    suspend fun updateAnimal(animal: Animal) {
        animalDao.updateAnimal(animal)
    }

    suspend fun addAnimal(animal: Animal) {
        animalDao.addAnimal(animal)
    }

    suspend fun deleteAnimal(animal: Animal) {
        animalDao.deleteAnimal(animal)
    }

    companion object {
        private var INSTANCE: AnimalsRepository? = null

        fun initRepository(daoFactory: DaoFactory) {
            if (INSTANCE == null) {
                INSTANCE = AnimalsRepository(daoFactory)
            }
        }

        fun getRepository(): AnimalsRepository {
            return INSTANCE ?: throw IllegalStateException("AnimalsRepository must be initialized")
        }
    }
}