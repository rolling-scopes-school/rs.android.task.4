package com.example.workingwithstorage.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "film_table")
data class Film(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val title: String,
    val country: String,
    val year: Int
): Parcelable
