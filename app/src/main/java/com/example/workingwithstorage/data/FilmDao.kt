package com.example.workingwithstorage.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.workingwithstorage.model.Film
import kotlinx.coroutines.flow.Flow


@Dao
interface FilmDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFilm(film: Film)

    @Update
    suspend fun updateFilm (film: Film)

    @Delete
    suspend fun deleteFilm (film: Film)

    @Query ("SELECT * FROM film_table ORDER BY id ASC")
    fun readAllData (): Flow<List<Film>>

    @Query("SELECT * FROM film_table ORDER BY title ASC")
    fun sortedByTitle (): Flow<List<Film>>

    @Query("SELECT * FROM film_table ORDER BY country ASC")
    fun sortedByCountry (): Flow<List<Film>>

    @Query("SELECT * FROM film_table ORDER BY year ASC")
    fun sortedByYear (): Flow<List<Film>>



}