package com.omelchenkoaleks.storageroomorcursor.screens.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.omelchenkoaleks.storageroomorcursor.database.AnimalsRepository
import com.omelchenkoaleks.storageroomorcursor.model.Animal

class AnimalsViewModel : ViewModel() {
    private var sort: MutableLiveData<String> = MutableLiveData("animal")

    private val animalsRepository = AnimalsRepository.getRepository()
    var animals: LiveData<List<Animal>> = Transformations.switchMap(sort) { order ->
        animalsRepository.getAnimals(order)
    }

    fun sortBy(order: String) {
        sort.value = order
    }
}