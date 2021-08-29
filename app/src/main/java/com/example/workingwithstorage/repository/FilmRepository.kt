package com.example.workingwithstorage.repository

import androidx.lifecycle.LiveData
import com.example.workingwithstorage.data.FilmDao
import com.example.workingwithstorage.model.Film

class FilmRepository (private val filmDao: FilmDao) {
    val readAllData: LiveData<List<Film>> = filmDao.readAllData()

    suspend fun addFilm(film: Film){
        filmDao.addFilm(film)
    }

    suspend fun updateFilm (film: Film){
        filmDao.updateFilm(film)
    }

    suspend fun deleteFilm (film: Film){
        filmDao.deleteFilm(film)
    }


}