package rs.android.task4.repository

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "animals_room")
data class Animal(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    val name: String,

    val age: Int = 0,

    val breed: String
) : Parcelable

