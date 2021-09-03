package com.example.workingwithstorage.common

import androidx.lifecycle.LiveData
import com.example.workingwithstorage.model.Film

enum class OrderSort (){
   BY_ID, BY_TITLE, BY_COUNTRY, BY_YEAR
  }
//fun sortFilm(order: OrderSort): LiveData<List<Film>> = when (order){
//    OrderSort.BY_ID -> readAllData ()
//    OrderSort.BY_TITLE -> sortedByTitle ()
//    OrderSort.BY_COUNTRY -> sortedByCountry ()
//    OrderSort.BY_YEAR -> sortedByYear ()
//}