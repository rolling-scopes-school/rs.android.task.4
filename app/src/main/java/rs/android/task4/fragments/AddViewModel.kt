package rs.android.task4.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import rs.android.task4.locator.locateLazy
import rs.android.task4.repository.Repository
import rs.android.task4.repository.Animal

class AddViewModel : ViewModel() {
    private val repository: Repository by locateLazy()

    fun save(name: String, age: Int, breed: String) {
        viewModelScope.launch {
            repository.save(createAnimal(name, age, breed))
        }
    }

    fun update(animal: Animal) {
        viewModelScope.launch {
            repository.update(animal)
        }
    }

    fun delete(animal: Animal) {
        viewModelScope.launch {
            repository.delete(animal)
        }
    }

    private fun createAnimal(name: String, age: Int, breed: String) = Animal(0, name, age, breed)

}