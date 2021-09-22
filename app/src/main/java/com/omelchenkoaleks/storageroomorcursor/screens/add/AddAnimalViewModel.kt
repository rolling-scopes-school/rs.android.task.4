package com.omelchenkoaleks.storageroomorcursor.screens.add

import androidx.lifecycle.*
import com.omelchenkoaleks.storageroomorcursor.database.AnimalsRepository
import com.omelchenkoaleks.storageroomorcursor.model.Animal
import kotlinx.coroutines.launch

class AddAnimalViewModel : ViewModel() {
    private val animalsRepository = AnimalsRepository.getRepository()
    private val animalsIdLiveData = MutableLiveData<Int>()
    var animal: Animal = Animal()
    val animals: LiveData<Animal?> = Transformations.switchMap(animalsIdLiveData) { animalsId ->
        animalsRepository.getAnimals(animalsId)
    }

    fun addAnimal(animal: Animal) {
        viewModelScope.launch {
            animalsRepository.addAnimal(animal)
        }
    }

    fun load(animalId: Int) {
        if (animalId != animalsIdLiveData.value)
            animalsIdLiveData.value = animalId
    }

    fun updateAnimal(animal: Animal) {
        viewModelScope.launch {
            animalsRepository.updateAnimal(animal)
        }
    }


}