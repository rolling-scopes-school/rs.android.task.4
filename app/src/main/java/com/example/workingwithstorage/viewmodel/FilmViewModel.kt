package com.example.workingwithstorage.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.workingwithstorage.data.PreferenceManager
import com.example.workingwithstorage.data.SQLite.FilmSQLiteHelper
import com.example.workingwithstorage.data.SQLite.SQLiteDao
import com.example.workingwithstorage.data.room.FilmDatabase
import com.example.workingwithstorage.model.Film
import com.example.workingwithstorage.repository.FilmRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

@InternalCoroutinesApi
class FilmViewModel(application: Application): AndroidViewModel (application) {
    private val repository: FilmRepository
    var allFilm:LiveData<List<Film>>


    init {
        val filmBDRoom = FilmDatabase.getDatabase(application, viewModelScope ).filmDao()
        val preferenceManager = PreferenceManager(application)
        val filmBDLite = SQLiteDao(
            sqlLite = FilmSQLiteHelper(application),
            preferencesManager = preferenceManager,
        )
        repository = FilmRepository(filmBDLite, filmBDRoom, preferenceManager)
        allFilm = repository.getAll().asLiveData()
    }


    fun sortedByTitle(){
        viewModelScope.launch (Dispatchers.IO){
            allFilm = repository.sortedByTitle().asLiveData()
        }
    }

    fun sortedByCountry(){
        viewModelScope.launch (Dispatchers.IO){
            allFilm = repository.sortedByCountry().asLiveData()
        }
    }

    fun sortedByYear(){
        viewModelScope.launch (Dispatchers.IO){
            allFilm = repository.sortedByYear().asLiveData()
        }
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

    override fun onCleared() {
        repository.job.cancel()
        super.onCleared()
    }
}