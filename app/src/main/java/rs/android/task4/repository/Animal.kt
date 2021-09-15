package rs.android.task4.repository

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.*
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "animals_room")
data class Animal(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @NonNull
    val name: String,

    @NonNull
    val age: Int = 0,

    @NonNull
    val breed: String
) : Parcelable

