package com.example.workingwithstorage.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.workingwithstorage.data.FilmDatabase
import com.example.workingwithstorage.model.Film
import com.example.workingwithstorage.repository.FilmRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

@InternalCoroutinesApi
class FilmViewModel(application: Application): AndroidViewModel (application) {
    val readAllData: LiveData<List<Film>>
    private val repository: FilmRepository
    init {
        val filmDao = FilmDatabase.getDatabase(application).filmDao()
        repository = FilmRepository(filmDao)
        readAllData = repository.readAllData
    }

    fun addFilm(film: Film){
        viewModelScope.launch (Dispatchers.IO){
            repository.addFilm(film)
        }
    }

    fun updateFilm (film: Film){
        viewModelScope.launch (Dispatchers.IO){
            repository.updateFilm(film)
        }
    }

    fun deleteFilm(film: Film){
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteFilm(film)
        }
    }
}