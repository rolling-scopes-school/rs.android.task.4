package com.omelchenkoaleks.storageroomorcursor.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_animal")
data class Animal(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var name: String = "",
    var age: Int = 0,
    var breed: String = ""
)