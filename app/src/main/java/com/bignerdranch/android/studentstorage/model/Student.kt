package com.bignerdranch.android.studentstorage.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Student(@PrimaryKey(autoGenerate = true)
                   val id: Int = 0,
                   var name: String = "",
                   var age: Int = 0,
                   var rating: Float = 0.0f): Parcelable