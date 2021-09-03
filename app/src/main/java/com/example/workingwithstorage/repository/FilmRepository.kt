package com.example.workingwithstorage.repository


import com.example.workingwithstorage.data.FilmDao
import com.example.workingwithstorage.model.Film
import kotlinx.coroutines.flow.Flow


class FilmRepository (private val filmDao: FilmDao) {


    fun getAll (): Flow<List<Film>> {
        return filmDao.readAllData()
    }

    fun sortedByTitle (): Flow<List<Film>> {
        return filmDao.sortedByTitle()
    }

    fun sortedByCountry (): Flow<List<Film>> {
        return filmDao.sortedByCountry()
    }

    fun sortedByYear (): Flow<List<Film>> {
        return filmDao.sortedByYear()
    }

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